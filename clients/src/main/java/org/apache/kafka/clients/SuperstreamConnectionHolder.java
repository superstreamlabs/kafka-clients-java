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
                }
            }
        }
    }

    public static Superstream getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Superstream has not been initialized");
        }
        return instance;
    }
}