package org.apache.kafka.common.superstream;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SuperstreamCounters {
    @JsonProperty("total_bytes_reduced")
    public AtomicLong TotalBytesReduced = new AtomicLong(0);

    @JsonProperty ("total_messages_successfully_produce")
    public AtomicInteger TotalMessagesSuccessfullyProduce = new AtomicInteger(0);
    @JsonProperty("total_messages_successfully_consume")
    public AtomicInteger TotalMessagesSuccessfullyConsumed = new AtomicInteger(0);
    @JsonProperty("total_messages_failed_produce")
    public AtomicInteger TotalMessagesFailedProduce = new AtomicInteger(0);
    @JsonProperty("total_messages_failed_consume")
    public AtomicInteger TotalMessagesFailedConsume = new AtomicInteger(0);

    public SuperstreamCounters() {
    }

    public void reset() {
        TotalBytesReduced = new AtomicLong(0);
        TotalMessagesSuccessfullyProduce = new AtomicInteger(0);
        TotalMessagesSuccessfullyConsumed = new AtomicInteger(0);
        TotalMessagesFailedProduce = new AtomicInteger(0);
        TotalMessagesFailedConsume = new AtomicInteger(0);
    }

    public void incrementTotalBytesReduced(long bytes) {
        TotalBytesReduced.addAndGet(bytes);
    }

    public void incrementTotalMessagesSuccessfullyProduce() {
        TotalMessagesSuccessfullyProduce.incrementAndGet();
    }


    public void incrementTotalMessagesSuccessfullyConsumed() {
        TotalMessagesSuccessfullyConsumed.incrementAndGet();
    }

    public void incrementTotalMessagesFailedProduce() {
        TotalMessagesFailedProduce.incrementAndGet();
    }

    public void incrementTotalMessagesFailedConsume() {
        TotalMessagesFailedConsume.incrementAndGet();
    }

    public int getTotalMessagesSuccessfullyProduce() {
        return TotalMessagesSuccessfullyProduce.get();
    }

    public int getTotalMessagesSuccessfullyConsumed() {
        return TotalMessagesSuccessfullyConsumed.get();
    }

    public int getTotalMessagesFailedProduce() {
        return TotalMessagesFailedProduce.get();
    }

    public int getTotalMessagesFailedConsume() {
        return TotalMessagesFailedConsume.get();
    }

    public void setTotalBytesBeforeReduction(long bytesReduced) {
        TotalBytesReduced = new AtomicLong(bytesReduced);
    }

    public void setTotalMessagesSuccessfullyProduce(int totalMessagesSuccessfullyProduce) {
        TotalMessagesSuccessfullyProduce.addAndGet(totalMessagesSuccessfullyProduce);
    }

    public void setTotalMessagesSuccessfullyConsumed(int totalMessagesSuccessfullyConsumed) {
        TotalMessagesSuccessfullyConsumed.addAndGet(totalMessagesSuccessfullyConsumed);
    }

    public void setTotalMessagesFailedProduce(int totalMessagesFailedProduce) {
        TotalMessagesFailedProduce.addAndGet(totalMessagesFailedProduce);
    }

    public void setTotalMessagesFailedConsume(int totalMessagesFailedConsume) {
        TotalMessagesFailedConsume.addAndGet(totalMessagesFailedConsume);
    }
}



