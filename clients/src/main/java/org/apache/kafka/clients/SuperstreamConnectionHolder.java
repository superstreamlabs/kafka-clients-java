package org.apache.kafka.clients;

import ai.superstream.Superstream;

public class SuperstreamConnectionHolder {
    private static volatile Superstream instance;
    private static final Object lock = new Object();

    public static void initialize(Superstream connection) {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = connection;
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