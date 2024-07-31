package org.apache.kafka.common.superstream;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

public class SuperstreamConsumerInterceptor<K, V> implements ConsumerInterceptor<K, V> {
    Superstream superstreamConnection;

    public SuperstreamConsumerInterceptor() {
    }

    @Override
    public ConsumerRecords<K, V> onConsume(ConsumerRecords<K, V> records) {
        if (this.superstreamConnection != null) {
            if (!records.isEmpty()) {
                for (ConsumerRecord<K, V> record : records) {
                    this.superstreamConnection.updateTopicPartitions(record.topic(), record.partition());
                    this.superstreamConnection.clientCounters
                            .incrementTotalReadBytesReduced(record.serializedValueSize());
                }
            }
        }
        ;
        return records;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
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
