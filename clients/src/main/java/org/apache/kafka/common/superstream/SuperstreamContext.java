package org.apache.kafka.common.superstream;

import org.apache.kafka.clients.NetworkClient;

import java.util.concurrent.ConcurrentHashMap;

public class SuperstreamContext {
    private static final ConcurrentHashMap<Thread, NetworkClient> threadToClientMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, Superstream> threadIdToSuperstreamMap = new ConcurrentHashMap<>();

    public static void registerClient(NetworkClient client) {
        threadToClientMap.put(Thread.currentThread(), client);
    }

    public static void unregisterClient() {
        threadToClientMap.remove(Thread.currentThread());
    }

    public static void setSuperstreamConnection() {
        NetworkClient client = threadToClientMap.get(Thread.currentThread());
        if (client != null && client.superstreamConnection != null) {
            threadIdToSuperstreamMap.put(Thread.currentThread().getId(), client.superstreamConnection);
        } else {
            throw new IllegalStateException("No NetworkClient registered for current thread or Superstream connection is null");
        }
    }

    public static Superstream getSuperstreamConnection() {
        return threadIdToSuperstreamMap.get(Thread.currentThread().getId());
    }

    public static void clear() {
        threadIdToSuperstreamMap.remove(Thread.currentThread().getId());
    }
}

