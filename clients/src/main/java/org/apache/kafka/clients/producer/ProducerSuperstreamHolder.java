package org.apache.kafka.clients.producer;

import ai.superstream.Superstream;
import ai.superstream.Consts;

import java.util.Map;

public class ProducerSuperstreamHolder {
    private static volatile Superstream instance;
    private static final Object lock = new Object();

    public static void initialize(Map<String, Object> configs) {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    String token = (String) configs.get(Consts.superstreamTokenKey);
                    String host = (String) configs.get(Consts.superstreamHostKey);
                    Integer learningFactor = (Integer) configs.get(Consts.superstreamLearningFactorKey);
                    Boolean reductionEnabled = (Boolean) configs.get(Consts.superstreamReductionEnabledKey);
                    String tags = (String) configs.get(Consts.superstreamTagsKey);

                    instance = new Superstream(token, host, learningFactor, configs, reductionEnabled, "producer", tags);
                    instance.init();
                }
            }
        }
    }

    public static Superstream getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Superstream has not been initialized for the producer");
        }
        return instance;
    }
}