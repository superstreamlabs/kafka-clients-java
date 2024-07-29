package org.apache.kafka.common.superstream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.function.Consumer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import io.nats.client.Subscription;
import io.nats.client.api.ServerInfo;
import io.nats.client.ConnectionListener;
import io.nats.client.Dispatcher;
import io.nats.client.JetStream;
import io.nats.client.Message;
import io.nats.client.MessageHandler;

public class Superstream {
    public Connection brokerConnection;
    public JetStream jetstream;
    public String natsConnectionID;
    public String clientHash;
    public String accountName;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public Map<String, Object> configs;
    public SuperstreamCounters clientCounters;
    private final String host;
    private final String token;
    public String type;
    public Map<String, Set<Integer>> topicPartitions = new ConcurrentHashMap<>();
    public ExecutorService executorService = Executors.newFixedThreadPool(3);
    private Integer kafkaConnectionID = 0;
    public Boolean superstreamReady = false;
    private final String tags;
    public Boolean canStart = false;
    private CompressionUpdateCallback compressionUpdateCallback;
    public Boolean compressionEnabled;

    private final ConcurrentHashMap<String, AtomicReference<SuperstreamCounters>> clientCountersMap = new ConcurrentHashMap<>();

    public Superstream(String token, String host, Map<String, Object> configs,
                       String type, String tags) {
        this.token = token;
        this.host = host;
        this.configs = configs;
        this.type = type;
        this.tags = tags;
        this.compressionEnabled = getBooleanEnv("SUPERSTREAM_COMPRESSION_ENABLED", false);
    }

    public Superstream(String token, String host, Map<String, Object> configs, String type) {
        this(token, host, configs, type, "");
    }

    public void init() {
        executorService.submit(() -> {
            try {
                initializeNatsConnection(token, host);
                if (this.brokerConnection != null) {
                    registerClient(configs);
                    waitForStart();
                    if (!canStart) {
                        throw new Exception("Could not start superstream");
                    }
                    subscribeToUpdates();
                    superstreamReady = true;
                    reportClientsUpdate();
                    sendClientTypeUpdateReq();
                }
            } catch (Exception e) {
                handleError(e.getMessage());
            }
        });
    }

    public void close() {
        try {
            if (brokerConnection != null) {
                brokerConnection.close();
            }
            executorService.shutdown();
        } catch (Exception ignored) {
        }
    }

    public void setCompressionUpdateCallback(CompressionUpdateCallback callback) {
        this.compressionUpdateCallback = callback;
    }

    public void updateClientCounters(Consumer<SuperstreamCounters> updateFunction) {
        clientCountersMap.compute(clientHash, (key, counterRef) -> {
            if (counterRef == null) {
                counterRef = new AtomicReference<>(this.clientCounters);
            }
            counterRef.updateAndGet(counters -> {
                updateFunction.accept(counters);
                return counters;
            });
            return counterRef;
        });
    }

    private Boolean getBooleanEnv(String key, Boolean defaultValue) {
        String value = System.getenv(key);
        return (value != null) ? Boolean.parseBoolean(value) : defaultValue;
    }

    public interface CompressionUpdateCallback {
        void onCompressionUpdate(boolean enabled, String type);
    }

    private void initializeNatsConnection(String token, String host) {
        try {
            Options options = new Options.Builder()
                    .server(host)
                    .userInfo(Consts.superstreamInternalUsername, token)
                    .maxReconnects(-1)
                    .connectionTimeout(Duration.ofSeconds(10))
                    .reconnectWait(Duration.ofSeconds(1))
                    .connectionListener(new ConnectionListener() {
                        @Override
                        public void connectionEvent(Connection conn, Events type) {
                            if (type == Events.DISCONNECTED) {
                                brokerConnection = null;
                                superstreamReady = false;
                                System.out.println("superstream: Disconnected");
                            } else if (type == Events.RECONNECTED) {
                                try {
                                    brokerConnection = conn;
                                    if (brokerConnection != null) {
                                        natsConnectionID = generateNatsConnectionID();
                                        Map<String, Object> reqData = new HashMap<>();
                                        reqData.put("new_nats_connection_id", natsConnectionID);
                                        reqData.put("client_hash", clientHash);
                                        ObjectMapper mapper = new ObjectMapper();
                                        byte[] reqBytes = mapper.writeValueAsBytes(reqData);
                                        brokerConnection.publish(Consts.clientReconnectionUpdateSubject, reqBytes);
                                        subscribeToUpdates();
                                        superstreamReady = true;
                                        reportClientsUpdate();
                                    }
                                } catch (Exception e) {
                                    System.out.println(
                                            "superstream: Failed to reconnect: " + e.getMessage());
                                }
                                System.out.println("superstream: Reconnected to superstream");
                            }
                        }
                    })
                    .build();

            try {
                brokerConnection = Nats.connect(options);
                jetstream = brokerConnection.jetStream();
            } catch (Exception e) {
                throw new Exception(String.format("Failed to connect to host: %s", host), e);
            }
            natsConnectionID = generateNatsConnectionID();
        } catch (Exception e) {
            System.out.printf("superstream: %s%n", e.getMessage());
        }
    }

    private String generateNatsConnectionID() {
        ServerInfo serverInfo = brokerConnection.getServerInfo();
        String connectedServerName = serverInfo.getServerName();
        int serverClientID = serverInfo.getClientId();
        return connectedServerName + ":" + serverClientID;
    }

    public void registerClient(Map<String, ?> configs) {
        try {
            String kafkaConnID = consumeConnectionID();

            if (!kafkaConnID.equals("0")) {
                try {
                    kafkaConnectionID = Integer.parseInt(kafkaConnID);
                } catch (Exception e) {
                    kafkaConnectionID = 0;
                }
            }
            this.clientCounters = new SuperstreamCounters(kafkaConnectionID);
            Map<String, Object> reqData = new HashMap<>();
            reqData.put("nats_connection_id", natsConnectionID);
            reqData.put("language", "java");
            reqData.put("version", Consts.sdkVersion);
            reqData.put("config", normalizeClientConfig(configs));
            reqData.put("compression_enabled", compressionEnabled);
            reqData.put("connection_id", kafkaConnectionID);
            reqData.put("tags", tags);
            ObjectMapper mapper = new ObjectMapper();
            byte[] reqBytes = mapper.writeValueAsBytes(reqData);
            Message reply = brokerConnection.request(Consts.clientRegisterSubject, reqBytes, Duration.ofMinutes(5));
            if (reply != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> replyData = mapper.readValue(reply.getData(), Map.class);
                Object clientHashObject = replyData.get("client_hash");
                if (clientHashObject != null) {
                    clientHash = clientHashObject.toString();
                } else {
                    System.out.println("superstream: client_hash is not a valid string: " + clientHashObject);
                }
                Object accountNameObject = replyData.get("account_name");
                if (accountNameObject != null) {
                    accountName = accountNameObject.toString();
                } else {
                    System.out.println("superstream: account_name is not a valid string: " + accountNameObject);
                }
            } else {
                String errMsg = "superstream: registering client: No reply received within the timeout period.";
                System.out.println(errMsg);
                handleError(errMsg);
            }
        } catch (Exception e) {
            System.out.printf("superstream: %s%n", e.getMessage());
        }
    }

    private void waitForStart() {
        CountDownLatch latch = new CountDownLatch(1);
        Dispatcher dispatcher = brokerConnection.createDispatcher((msg) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map messageData = mapper.readValue(msg.getData(), Map.class);
                if (messageData.containsKey("start")) {
                    boolean start = (Boolean) messageData.get("start");
                    if (start) {
                        canStart = true;
                        latch.countDown(); // continue and stop the wait
                    } else {
                        String err = (String) messageData.get("error");
                        System.out.println("superstream: Could not start superstream: " + err);
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (Exception e) {
                System.out.println("superstream: Exception: " + e.getMessage());
            }
        });

        dispatcher.subscribe(String.format(Consts.clientStartSubject, clientHash)); // replace with your specific subject

        try {
            if (!latch.await(10, TimeUnit.MINUTES)) {
                System.out.println("superstream: Could not connect to superstream for 10 minutes.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("superstream: Could not start superstream: " + e.getMessage());
        } finally {
            dispatcher.unsubscribe(String.format(Consts.clientStartSubject, clientHash));
        }
    }

    private String consumeConnectionID() {
        Properties consumerProps = copyAuthConfig();
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerProps.put(Consts.superstreamInnerConsumerKey, "true");
        consumerProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        String connectionId = null;
        KafkaConsumer<String, String> consumer = null;
        try {
            consumer = new KafkaConsumer<>(consumerProps);
            List<PartitionInfo> partitions = consumer.partitionsFor(Consts.superstreamMetadataTopic, Duration.ofMillis(10000));
            if (partitions == null || partitions.isEmpty()) {
                if (consumer != null) {
                    consumer.close();
                }
                return "0";
            }
            TopicPartition topicPartition = new TopicPartition(Consts.superstreamMetadataTopic, 0);
            consumer.assign(Collections.singletonList(topicPartition));
            consumer.seekToEnd(Collections.singletonList(topicPartition));
            long endOffset = consumer.position(topicPartition);
            if (endOffset > 0) {
                consumer.seek(topicPartition, endOffset - 1);
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
                if (!records.isEmpty()) {
                    connectionId = records.iterator().next().value();
                }
            }
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().contains("timeout")) {
                try {
                    Thread.sleep(10000);
                    if (consumer == null) {
                        consumer = new KafkaConsumer<>(consumerProps);
                    }
                    List<PartitionInfo> partitions = consumer.partitionsFor(Consts.superstreamMetadataTopic, Duration.ofMillis(10000));
                    if (partitions == null || partitions.isEmpty()) {
                        if (consumer != null) {
                            consumer.close();
                        }
                        return "0";
                    }
                    TopicPartition topicPartition = new TopicPartition(Consts.superstreamMetadataTopic, 0);
                    consumer.assign(Collections.singletonList(topicPartition));
                    consumer.seekToEnd(Collections.singletonList(topicPartition));
                    long endOffset = consumer.position(topicPartition);
                    if (endOffset > 0) {
                        consumer.seek(topicPartition, endOffset - 1);
                        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
                        if (!records.isEmpty()) {
                            connectionId = records.iterator().next().value();
                        }
                    }
                } catch (Exception e2) {
                    handleError(String.format("consumeConnectionID retry: %s", e2.getMessage()));
                }
            }
            if (connectionId == null || connectionId.equals("0")) {
                handleError(String.format("consumeConnectionID: %s", e.getMessage()));
                if (consumer != null) {
                    consumer.close();
                }
                return "0";
            }
        } finally {
            if (consumer != null) {
                consumer.close();
            }
        }
        return connectionId != null ? connectionId : "0";
    }

    private Properties copyAuthConfig() {
        String[] relevantKeys = {
                // Authentication-related keys
                "security.protocol",
                "ssl.truststore.location",
                "ssl.truststore.password",
                "ssl.keystore.location",
                "ssl.keystore.password",
                "ssl.key.password",
                "ssl.endpoint.identification.algorithm",
                "sasl.mechanism",
                "sasl.jaas.config",
                "sasl.kerberos.service.name",
                // Networking-related keys
                "bootstrap.servers",
                "client.dns.lookup",
                "connections.max.idle.ms",
                "request.timeout.ms",
                "metadata.max.age.ms",
                "reconnect.backoff.ms",
                "reconnect.backoff.max.ms"
        };

        Properties relevantProps = new Properties();
        for (String key : relevantKeys) {
            if (configs.containsKey(key)) {
                if (key.equals(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)) {
                    Object value = configs.get(key);
                    if (value instanceof String[]) {
                        relevantProps.put(key, Arrays.toString((String[]) value));
                    } else if (value instanceof ArrayList) {
                        @SuppressWarnings("unchecked")
                        ArrayList<String> arrayList = (ArrayList<String>) value;
                        relevantProps.put(key, String.join(", ", arrayList));
                    } else {
                        relevantProps.put(key, value);
                    }
                } else {
                    relevantProps.put(key, String.valueOf(configs.get(key)));
                }
            }
        }
        return relevantProps;
    }

    public void sendClientTypeUpdateReq() {
        if (Objects.equals(type, "") || type == null) {
            return;
        }
        if (!type.equals("consumer") && !type.equals("producer")) {
            return;
        }
        try {
            Map<String, Object> reqData = new HashMap<>();
            reqData.put("client_hash", clientHash);
            reqData.put("type", type);
            ObjectMapper mapper = new ObjectMapper();
            byte[] reqBytes = mapper.writeValueAsBytes(reqData);
            brokerConnection.publish(Consts.clientTypeUpdateSubject, reqBytes);
        } catch (Exception e) {
            handleError(String.format("sendClientTypeUpdateReq: %s", e.getMessage()));
        }
    }

    public void subscribeToUpdates() {
        try {
            String subject = String.format(Consts.superstreamUpdatesSubject, clientHash);
            Dispatcher dispatcher = brokerConnection.createDispatcher(this.updatesHandler());
            dispatcher.subscribe(subject, this.updatesHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportClientsUpdate() {
        ScheduledExecutorService singleExecutorService = Executors.newSingleThreadScheduledExecutor();
        singleExecutorService.scheduleAtFixedRate(() -> {
            try {
                if (brokerConnection != null && superstreamReady){
                    byte[] byteCounters = objectMapper.writeValueAsBytes(clientCounters);
                    AtomicReference<SuperstreamCounters> countersRef = clientCountersMap.get(clientHash);
                    if (countersRef != null) {
                        SuperstreamCounters currentCounters = countersRef.get();

                        if (currentCounters != null) {
                            byteCounters = objectMapper.writeValueAsBytes(currentCounters);
                            clientCounters.reset();
                        }
                    }

                    Map<String, Object> topicPartitionConfig = new HashMap<>();
                    if (!topicPartitions.isEmpty()) {
                        Map<String, Integer[]> topicPartitionsToSend = convertMap(topicPartitions);
                        switch (this.type) {
                            case "producer":
                                topicPartitionConfig.put("producer_topics_partitions", topicPartitionsToSend);
                                topicPartitionConfig.put("consumer_group_topics_partitions",
                                        new HashMap<String, Integer[]>());
                                break;
                            case "consumer":
                                topicPartitionConfig.put("producer_topics_partitions", new HashMap<String, Integer[]>());
                                topicPartitionConfig.put("consumer_group_topics_partitions", topicPartitionsToSend);
                                break;
                        }
                    }
                    byte[] byteConfig = objectMapper.writeValueAsBytes(topicPartitionConfig);
                    brokerConnection.publish(String.format(Consts.superstreamClientsUpdateSubject, "counters", clientHash),
                            byteCounters);
                    brokerConnection.publish(String.format(Consts.superstreamClientsUpdateSubject, "config", clientHash),
                            byteConfig);

                }
            } catch (Exception e) {
                handleError("reportClientsUpdate: " + e.getMessage());
            }
        }, 0, 10, TimeUnit.MINUTES);
    }

    public static Map<String, Integer[]> convertMap(Map<String, Set<Integer>> topicPartitions) {
        Map<String, Integer[]> result = new HashMap<>();
        for (Map.Entry<String, Set<Integer>> entry : topicPartitions.entrySet()) {
            Integer[] array = entry.getValue().toArray(new Integer[0]);
            result.put(entry.getKey(), array);
        }
        return result;
    }

    private MessageHandler updatesHandler() {
        return (msg) -> {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> update = objectMapper.readValue(msg.getData(), Map.class);
                processUpdate(update);
            } catch (IOException e) {
                handleError("updatesHandler at json.Unmarshal: " + e.getMessage());
            }
        };
    }

    private void processUpdate(Map<String, Object> update) {
        String type = (String) update.get("type");
        try {
            String payloadBytesString = (String) update.get("payload");
            byte[] payloadBytes = Base64.getDecoder().decode(payloadBytesString);
            @SuppressWarnings("unchecked")
            Map<String, Object> payload = objectMapper.readValue(payloadBytes, Map.class);
            switch (type) {
                case "CompressionUpdate":
                    Boolean enableCompression = (Boolean) payload.get("enable_compression");
                    String compressionType = (String) payload.get("compression_type");
                    this.compressionEnabled = enableCompression;
                    if (compressionUpdateCallback != null) {
                        compressionUpdateCallback.onCompressionUpdate(enableCompression, compressionType);
                    }
                    break;
                //TODO: add future cases
            }
        } catch (Exception e) {
            handleError(("processUpdate: " + e.getMessage()));
        }
    }

    public void handleError(String msg) {

        if (brokerConnection != null && superstreamReady) {
            Map<String, String> envVars = System.getenv();
            String tags = envVars.getOrDefault("SUPERSTREAM_TAGS", "");
            String message;
            if (Objects.equals(clientHash, "")) {
                message = String.format("[sdk: java][version: %s][tags: %s] %s", Consts.sdkVersion, tags, msg);
            } else {
                message = String.format("[clientHash: %s][sdk: java][version: %s][tags: %s] %s",
                        clientHash, Consts.sdkVersion, tags, msg);
            }
            brokerConnection.publish(Consts.superstreamErrorSubject, message.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static Map<String, Object> normalizeClientConfig(Map<String, ?> javaConfig) {
        Map<String, Object> superstreamConfig = new HashMap<>();

        // Producer configurations
        // Note: Handling of `producer_return_errors` and `producer_return_successes` is
        // typically done programmatically in the Java client,
        // `producer_flush_max_messages` does not exist in java
        mapIfPresent(javaConfig, ProducerConfig.MAX_REQUEST_SIZE_CONFIG, superstreamConfig,
                "producer_max_messages_bytes");
        mapIfPresent(javaConfig, ProducerConfig.ACKS_CONFIG, superstreamConfig, "producer_required_acks");
        mapIfPresent(javaConfig, ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, superstreamConfig, "producer_timeout");
        mapIfPresent(javaConfig, ProducerConfig.RETRIES_CONFIG, superstreamConfig, "producer_retry_max");
        mapIfPresent(javaConfig, ProducerConfig.RETRY_BACKOFF_MS_CONFIG, superstreamConfig, "producer_retry_backoff");
        mapIfPresent(javaConfig, ProducerConfig.COMPRESSION_TYPE_CONFIG, superstreamConfig,
                "producer_compression_level");
        // Consumer configurations
        // Note: `consumer_return_errors`, `consumer_offsets_initial`,
        // `consumer_offsets_retry_max`, `consumer_group_rebalance_timeout`,
        // `consumer_group_rebalance_retry_max` does not exist in java
        mapIfPresent(javaConfig, ConsumerConfig.FETCH_MIN_BYTES_CONFIG, superstreamConfig, "consumer_fetch_min");
        mapIfPresent(javaConfig, ConsumerConfig.FETCH_MAX_BYTES_CONFIG, superstreamConfig, "consumer_fetch_default");
        mapIfPresent(javaConfig, ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, superstreamConfig, "consumer_retry_backoff");
        mapIfPresent(javaConfig, ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, superstreamConfig,
                "consumer_max_wait_time");
        mapIfPresent(javaConfig, ConsumerConfig.MAX_POLL_RECORDS_CONFIG, superstreamConfig,
                "consumer_max_processing_time");
        // mapIfPresent(javaConfig, ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
        // superstreamConfig, "consumer_offset_auto_commit_enable");
        // TODO: handle boolean vars
        mapIfPresent(javaConfig, ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, superstreamConfig,
                "consumer_offset_auto_commit_interval");
        mapIfPresent(javaConfig, ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, superstreamConfig,
                "consumer_group_session_timeout");
        mapIfPresent(javaConfig, ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, superstreamConfig,
                "consumer_group_heart_beat_interval");
        mapIfPresent(javaConfig, ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, superstreamConfig,
                "consumer_group_rebalance_retry_back_off");
        // mapIfPresent(javaConfig, ConsumerConfig.AUTO_OFFSET_RESET_CONFIG ,
        // superstreamConfig, "consumer_group_rebalance_reset_invalid_offsets"); //
        // TODO: handle boolean vars
        mapIfPresent(javaConfig, ConsumerConfig.GROUP_ID_CONFIG, superstreamConfig, "consumer_group_id");
        // Common configurations
        mapIfPresent(javaConfig, ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, superstreamConfig, "servers");
        // Note: No access to `producer_topics_partitions` and
        // `consumer_group_topics_partitions`
        return superstreamConfig;
    }

    private static void mapIfPresent(Map<String, ?> javaConfig, String javaKey, Map<String, Object> superstreamConfig,
                                     String superstreamKey) {
        if (javaConfig.containsKey(javaKey)) {
            if (Objects.equals(javaKey, ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)) {
                Object value = javaConfig.get(javaKey);
                if (value instanceof String[]) {
                    superstreamConfig.put(superstreamKey, Arrays.toString((String[]) value));
                } else if (value instanceof ArrayList) {
                    @SuppressWarnings("unchecked")
                    ArrayList<String> arrayList = (ArrayList<String>) value;
                    superstreamConfig.put(superstreamKey, String.join(", ", arrayList));
                } else {
                    superstreamConfig.put(superstreamKey, value);
                }
            } else {
                superstreamConfig.put(superstreamKey, javaConfig.get(javaKey));
            }
        }
    }

    public static Map<String, Object> initSuperstreamConfig(Map<String, Object> configs, String type) {
        String isInnerConsumer = (String) configs.get(Consts.superstreamInnerConsumerKey);
        if (isInnerConsumer != null && isInnerConsumer.equals("true")) {
            return configs;
        }
        String interceptorToAdd = "";
        switch (type) {
            case "producer":
                //TODO: add producer future logic
                break;
            case "consumer":
                //TODO: add consumer future logic
                break;
        }

        try {
            Map<String, String> envVars = System.getenv();
            String superstreamHost = envVars.get("SUPERSTREAM_HOST");
            if (superstreamHost == null) {
                throw new Exception("host is required");
            }
            configs.put(Consts.superstreamHostKey, superstreamHost);
            String token = envVars.get("SUPERSTREAM_TOKEN");
            if (token == null) {
                token = Consts.superstreamDefaultToken;
            }
            configs.put(Consts.superstreamTokenKey, token);
            String learningFactorString = envVars.get("SUPERSTREAM_LEARNING_FACTOR");
            Integer learningFactor = Consts.superstreamDefaultLearningFactor;
            if (learningFactorString != null) {
                learningFactor = Integer.parseInt(learningFactorString);
            }
            configs.put(Consts.superstreamLearningFactorKey, learningFactor);

            String tags = envVars.get("SUPERSTREAM_TAGS");
            if (tags == null) {
                tags = "";
            }
            Superstream superstreamConnection = new Superstream(token, superstreamHost, configs, type, tags);
            superstreamConnection.init();
            configs.put(Consts.superstreamConnectionKey, superstreamConnection);
        } catch (Exception e) {
            String errMsg = String.format("superstream: error initializing superstream: %s", e.getMessage());
            System.out.println(errMsg);
        }
        return configs;
    }
}
