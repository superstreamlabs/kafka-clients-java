package org.apache.kafka.common.superstream;

import org.apache.kafka.common.metrics.MetricsReporter;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.metrics.KafkaMetric;

import java.util.List;
import java.util.Map;

public class SuperstreamMetricsReporter implements MetricsReporter {
    Superstream superstreamConnection;

    @Override
    public void init(List<KafkaMetric> metrics) {
        System.out.println("Initializing metrics...");
        for (KafkaMetric metric : metrics) {
            System.out.println("Metric: " + metric.metricName().name());
        }
    }

    @Override
    public void metricChange(KafkaMetric metric) {
        MetricName name = metric.metricName();
        if (name.name().equals("compression-rate-avg")) {
            System.out.println("compression-rate-avg metric changed: " + metric.metricValue().toString());
            double compressionRate = (double) metric.metricValue();
            if (superstreamConnection != null) {
                superstreamConnection.clientCounters.setCompressionRate(compressionRate);
            }
        } else if (name.name().equals("compression-rate")) {
            System.out.println("compression-rate metric changed: " + metric.metricValue().toString());
            double compressionRate = (double) metric.metricValue();
            // if (superstreamConnection != null) {
            //     superstreamConnection.clientCounters.setCompressionRate(compressionRate);
            // }
        }
    }

    @Override
    public void metricRemoval(KafkaMetric metric) {
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> configs) {
        Superstream superstreamConn = (Superstream) configs.get(Consts.superstreamConnectionKey);
        if (superstreamConn != null) {
            this.superstreamConnection = superstreamConn;
        }
    }

}
