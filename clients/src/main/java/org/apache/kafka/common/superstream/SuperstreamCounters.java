package org.apache.kafka.common.superstream;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicLong;

public class SuperstreamCounters {
    @JsonProperty("total_read_bytes_reduced")
    public AtomicLong TotalReadBytesReduced = new AtomicLong(0);

    @JsonProperty("total_write_bytes_reduced")
    public AtomicLong TotalWriteBytesReduced = new AtomicLong(0);

    @JsonProperty("connection_id")
    public final Integer ConnectionId;


    public SuperstreamCounters(int connectionId) {
        ConnectionId = connectionId;
    }

    public void incrementTotalReadBytesReduced(long bytes) {
        TotalReadBytesReduced.addAndGet(bytes);
    }

    public void incrementTotalWriteBytesReduced(long bytes) {
        TotalWriteBytesReduced.addAndGet(bytes);
    }

    public long getTotalReadBytesReduced() {
        return TotalReadBytesReduced.get();
    }

    public long getTotalWriteBytesReduced() {
        return TotalWriteBytesReduced.get();
    }

    public void reset() {
        TotalReadBytesReduced.set(0);
        TotalWriteBytesReduced.set(0);
    }
}



