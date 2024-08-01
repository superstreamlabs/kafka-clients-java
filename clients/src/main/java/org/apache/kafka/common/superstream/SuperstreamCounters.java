package org.apache.kafka.common.superstream;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.metrics.Metrics;
import org.apache.kafka.common.Metric;
import java.util.Map;

public class SuperstreamCounters {
    @JsonProperty("total_read_bytes_reduced")
    public AtomicLong TotalReadBytesReduced = new AtomicLong(0);

    @JsonProperty("total_write_bytes_reduced")
    public AtomicLong TotalWriteBytesReduced = new AtomicLong(0);

    public AtomicLong TotalReadBytes = new AtomicLong(0);

    public Metrics metrics;

    public SuperstreamCounters() {
    }

    public void reset() {
        TotalReadBytesReduced.set(0);
        TotalWriteBytesReduced.set(0);
    }

    public void incrementTotalReadBytesReduced(long bytes) {
        TotalReadBytesReduced.addAndGet(bytes);
    }

    public void incrementTotalWriteBytesReduced(long bytes) {
        TotalWriteBytesReduced.addAndGet(bytes);
    }

    public void incrementTotalReadBytes(long bytes) {
        TotalReadBytes.addAndGet(bytes);
    }

    public long getTotalReadBytesReduced() {
        return TotalReadBytesReduced.get();
    }

    public long getTotalWriteBytesReduced() {
        return TotalWriteBytesReduced.get();
    }

    public long getTotalReadBytes() {
        return TotalReadBytes.get();
    }

    public Double getProducerCompressionRate() {
        Double rate = (Double) getProducerCompressionMetric();
        if (rate == null || rate.isNaN() || rate > 1.0 || rate == 1.0 || rate < 0.0) {
            return 0.0;
        }
        if (rate > 0.0 && rate < 1.0) {
            return (1-rate);
        }
        return 0.0;
    }

    public Double getConsumerCompressionRate() { 
        Double totalBytesCompressedConsumed = (Double) getConsumerBytesConsumedMetric();
        if (totalBytesCompressedConsumed == null || totalBytesCompressedConsumed.isNaN() || totalBytesCompressedConsumed <= 0.0 || getTotalReadBytes() <= 0) {
            return 0.0;
        }
        return (1 - (totalBytesCompressedConsumed / getTotalReadBytes()));
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }


    public Double getProducerCompressionMetric() {
        if (metrics != null) {
            for (Map.Entry<MetricName, ? extends Metric> entry : metrics.metrics().entrySet()) {
                String name = entry.getKey().name();
                if (name.equals("compression-rate-avg")) {
                    return entry.getValue().metricValue();
                }
            }
        }
        return 0.0; 
    }

    public Double getConsumerBytesConsumedMetric() {
        if (metrics != null) {
            for (Map.Entry<MetricName, ? extends Metric> entry : metrics.metrics().entrySet()) {
                String name = entry.getKey().name();
                if (name.equals("bytes-consumed-total")) {
                    return entry.getValue().metricValue();
                }
            } 
        }
        return 0.0;
    }
}
