package org.apache.kafka.common.superstream;

import java.util.HashMap;
import java.util.Map;

public class SuperstreamConfigParser {

    private Map<String, Boolean> longCastMap = null;
    private Map<String, Boolean> shortCastMap = null;
    private Map<String, Boolean> classCastMap = null;

    public SuperstreamConfigParser() {
        this.longCastMap = this.getLongCastMap();
        this.shortCastMap = this.getShortCastMap();
        this.classCastMap = this.getClassCastMap();
    }

    private Map<String, Boolean> getLongCastMap() {
        Map<String, Boolean> m = new HashMap<>();
        m.put("buffer.memory", true);
        m.put("linger.ms", true);
        m.put("connections.max.idle.ms", true);
        m.put("max.block.ms", true);
        m.put("metadata.max.age.ms", true);
        m.put("metadata.max.idle.ms", true);
        m.put("metrics.sample.window.ms", true);
        m.put("partitioner.availability.timeout.ms", true);
        m.put("reconnect.backoff.max.ms", true);
        m.put("reconnect.backoff.ms", true);
        m.put("retry.backoff.ms", true);
        m.put("sasl.kerberos.min.time.before.relogin", true);
        m.put("sasl.login.retry.backoff.max.ms", true);
        m.put("sasl.login.retry.backoff.ms", true);
        m.put("sasl.oauthbearer.jwks.endpoint.refresh.ms", true);
        return m;
    }

    private Map<String, Boolean> getShortCastMap() {
        Map<String, Boolean> m = new HashMap<>();
        m.put("sasl.login.refresh.min.period.seconds", true);
        m.put("sasl.login.refresh.buffer.seconds", true);
        return m;
    }

    private Map<String, Boolean> getClassCastMap() {
        Map<String, Boolean> m = new HashMap<>();
        m.put("alter.config.policy.class.name", true);
        m.put("create.topic.policy.class.name", true);
        m.put("partitioner.class", true);
        m.put("sasl.client.callback.handler.class", true);
        m.put("sasl.login.callback.handler.class", true);
        m.put("sasl.login.class", true);
        m.put("ssl.engine.factory.class", true);
        m.put("key.serializer", true);
        m.put("value.serializer", true);
        m.put("key.deserializer", true);
        m.put("value.deserializer", true);
        return m;
    }

    public Map<String, ?> parse(Map<String, Object> receivedConfig) throws ClassNotFoundException {
        for (Map.Entry<String, Object> entry : receivedConfig.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            if (longCastMap.containsKey(key)) {
                Long castedObj = Long.valueOf(value.toString());
                receivedConfig.put(key, castedObj);
                continue;
            }
            if (shortCastMap.containsKey(key)) {
                Short castedObj = Short.valueOf(value.toString());
                receivedConfig.put(key, castedObj);
                continue;
            }
            if (classCastMap.containsKey(key)) {
                Class<?> castedObj = Class.forName(value.toString());
                receivedConfig.put(key, castedObj);
                continue;
            }
            if (key.equals("acks") && value.equals("all")) {
                receivedConfig.put(key, "-1");
            }
        }
        return receivedConfig;
    }

}
