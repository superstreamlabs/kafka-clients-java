/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// THIS CODE IS AUTOMATICALLY GENERATED.  DO NOT EDIT.

package org.apache.kafka.coordinator.group.generated;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.common.Uuid;
import org.apache.kafka.common.errors.UnsupportedVersionException;
import org.apache.kafka.common.protocol.ApiMessage;
import org.apache.kafka.common.protocol.Message;
import org.apache.kafka.common.protocol.MessageSizeAccumulator;
import org.apache.kafka.common.protocol.MessageUtil;
import org.apache.kafka.common.protocol.ObjectSerializationCache;
import org.apache.kafka.common.protocol.Readable;
import org.apache.kafka.common.protocol.Writable;
import org.apache.kafka.common.protocol.types.CompactArrayOf;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.protocol.types.RawTaggedField;
import org.apache.kafka.common.protocol.types.RawTaggedFieldWriter;
import org.apache.kafka.common.protocol.types.Schema;
import org.apache.kafka.common.protocol.types.Type;
import org.apache.kafka.common.utils.ByteUtils;

import static org.apache.kafka.common.protocol.types.Field.TaggedFieldsSection;


public class ConsumerGroupPartitionMetadataValue implements ApiMessage {
    int epoch;
    List<TopicMetadata> topics;
    private List<RawTaggedField> _unknownTaggedFields;
    
    public static final Schema SCHEMA_0 =
        new Schema(
            new Field("epoch", Type.INT32, "The group epoch."),
            new Field("topics", new CompactArrayOf(TopicMetadata.SCHEMA_0), "The list of topic metadata."),
            TaggedFieldsSection.of(
            )
        );
    
    public static final Schema[] SCHEMAS = new Schema[] {
        SCHEMA_0
    };
    
    public static final short LOWEST_SUPPORTED_VERSION = 0;
    public static final short HIGHEST_SUPPORTED_VERSION = 0;
    
    public ConsumerGroupPartitionMetadataValue(Readable _readable, short _version) {
        read(_readable, _version);
    }
    
    public ConsumerGroupPartitionMetadataValue() {
        this.epoch = 0;
        this.topics = new ArrayList<TopicMetadata>(0);
    }
    
    @Override
    public short apiKey() {
        return -1;
    }
    
    @Override
    public short lowestSupportedVersion() {
        return 0;
    }
    
    @Override
    public short highestSupportedVersion() {
        return 0;
    }
    
    @Override
    public final void read(Readable _readable, short _version) {
        this.epoch = _readable.readInt();
        {
            int arrayLength;
            arrayLength = _readable.readUnsignedVarint() - 1;
            if (arrayLength < 0) {
                throw new RuntimeException("non-nullable field topics was serialized as null");
            } else {
                if (arrayLength > _readable.remaining()) {
                    throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                }
                ArrayList<TopicMetadata> newCollection = new ArrayList<>(arrayLength);
                for (int i = 0; i < arrayLength; i++) {
                    newCollection.add(new TopicMetadata(_readable, _version));
                }
                this.topics = newCollection;
            }
        }
        this._unknownTaggedFields = null;
        int _numTaggedFields = _readable.readUnsignedVarint();
        for (int _i = 0; _i < _numTaggedFields; _i++) {
            int _tag = _readable.readUnsignedVarint();
            int _size = _readable.readUnsignedVarint();
            switch (_tag) {
                default:
                    this._unknownTaggedFields = _readable.readUnknownTaggedField(this._unknownTaggedFields, _tag, _size);
                    break;
            }
        }
    }
    
    @Override
    public void write(Writable _writable, ObjectSerializationCache _cache, short _version) {
        int _numTaggedFields = 0;
        _writable.writeInt(epoch);
        _writable.writeUnsignedVarint(topics.size() + 1);
        for (TopicMetadata topicsElement : topics) {
            topicsElement.write(_writable, _cache, _version);
        }
        RawTaggedFieldWriter _rawWriter = RawTaggedFieldWriter.forFields(_unknownTaggedFields);
        _numTaggedFields += _rawWriter.numFields();
        _writable.writeUnsignedVarint(_numTaggedFields);
        _rawWriter.writeRawTags(_writable, Integer.MAX_VALUE);
    }
    
    @Override
    public void addSize(MessageSizeAccumulator _size, ObjectSerializationCache _cache, short _version) {
        int _numTaggedFields = 0;
        _size.addBytes(4);
        {
            _size.addBytes(ByteUtils.sizeOfUnsignedVarint(topics.size() + 1));
            for (TopicMetadata topicsElement : topics) {
                topicsElement.addSize(_size, _cache, _version);
            }
        }
        if (_unknownTaggedFields != null) {
            _numTaggedFields += _unknownTaggedFields.size();
            for (RawTaggedField _field : _unknownTaggedFields) {
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_field.tag()));
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_field.size()));
                _size.addBytes(_field.size());
            }
        }
        _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_numTaggedFields));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ConsumerGroupPartitionMetadataValue)) return false;
        ConsumerGroupPartitionMetadataValue other = (ConsumerGroupPartitionMetadataValue) obj;
        if (epoch != other.epoch) return false;
        if (this.topics == null) {
            if (other.topics != null) return false;
        } else {
            if (!this.topics.equals(other.topics)) return false;
        }
        return MessageUtil.compareRawTaggedFields(_unknownTaggedFields, other._unknownTaggedFields);
    }
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode = 31 * hashCode + epoch;
        hashCode = 31 * hashCode + (topics == null ? 0 : topics.hashCode());
        return hashCode;
    }
    
    @Override
    public ConsumerGroupPartitionMetadataValue duplicate() {
        ConsumerGroupPartitionMetadataValue _duplicate = new ConsumerGroupPartitionMetadataValue();
        _duplicate.epoch = epoch;
        ArrayList<TopicMetadata> newTopics = new ArrayList<TopicMetadata>(topics.size());
        for (TopicMetadata _element : topics) {
            newTopics.add(_element.duplicate());
        }
        _duplicate.topics = newTopics;
        return _duplicate;
    }
    
    @Override
    public String toString() {
        return "ConsumerGroupPartitionMetadataValue("
            + "epoch=" + epoch
            + ", topics=" + MessageUtil.deepToString(topics.iterator())
            + ")";
    }
    
    public int epoch() {
        return this.epoch;
    }
    
    public List<TopicMetadata> topics() {
        return this.topics;
    }
    
    @Override
    public List<RawTaggedField> unknownTaggedFields() {
        if (_unknownTaggedFields == null) {
            _unknownTaggedFields = new ArrayList<>(0);
        }
        return _unknownTaggedFields;
    }
    
    public ConsumerGroupPartitionMetadataValue setEpoch(int v) {
        this.epoch = v;
        return this;
    }
    
    public ConsumerGroupPartitionMetadataValue setTopics(List<TopicMetadata> v) {
        this.topics = v;
        return this;
    }
    
    public static class TopicMetadata implements Message {
        Uuid topicId;
        String topicName;
        int numPartitions;
        List<PartitionMetadata> partitionMetadata;
        private List<RawTaggedField> _unknownTaggedFields;
        
        public static final Schema SCHEMA_0 =
            new Schema(
                new Field("topic_id", Type.UUID, "The topic id."),
                new Field("topic_name", Type.COMPACT_STRING, "The topic name."),
                new Field("num_partitions", Type.INT32, "The number of partitions of the topic."),
                new Field("partition_metadata", new CompactArrayOf(PartitionMetadata.SCHEMA_0), "Partitions mapped to a set of racks. If the rack information is unavailable for all the partitions, an empty list is stored"),
                TaggedFieldsSection.of(
                )
            );
        
        public static final Schema[] SCHEMAS = new Schema[] {
            SCHEMA_0
        };
        
        public static final short LOWEST_SUPPORTED_VERSION = 0;
        public static final short HIGHEST_SUPPORTED_VERSION = 0;
        
        public TopicMetadata(Readable _readable, short _version) {
            read(_readable, _version);
        }
        
        public TopicMetadata() {
            this.topicId = Uuid.ZERO_UUID;
            this.topicName = "";
            this.numPartitions = 0;
            this.partitionMetadata = new ArrayList<PartitionMetadata>(0);
        }
        
        
        @Override
        public short lowestSupportedVersion() {
            return 0;
        }
        
        @Override
        public short highestSupportedVersion() {
            return 0;
        }
        
        @Override
        public final void read(Readable _readable, short _version) {
            if (_version > 0) {
                throw new UnsupportedVersionException("Can't read version " + _version + " of TopicMetadata");
            }
            this.topicId = _readable.readUuid();
            {
                int length;
                length = _readable.readUnsignedVarint() - 1;
                if (length < 0) {
                    throw new RuntimeException("non-nullable field topicName was serialized as null");
                } else if (length > 0x7fff) {
                    throw new RuntimeException("string field topicName had invalid length " + length);
                } else {
                    this.topicName = _readable.readString(length);
                }
            }
            this.numPartitions = _readable.readInt();
            {
                int arrayLength;
                arrayLength = _readable.readUnsignedVarint() - 1;
                if (arrayLength < 0) {
                    throw new RuntimeException("non-nullable field partitionMetadata was serialized as null");
                } else {
                    if (arrayLength > _readable.remaining()) {
                        throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                    }
                    ArrayList<PartitionMetadata> newCollection = new ArrayList<>(arrayLength);
                    for (int i = 0; i < arrayLength; i++) {
                        newCollection.add(new PartitionMetadata(_readable, _version));
                    }
                    this.partitionMetadata = newCollection;
                }
            }
            this._unknownTaggedFields = null;
            int _numTaggedFields = _readable.readUnsignedVarint();
            for (int _i = 0; _i < _numTaggedFields; _i++) {
                int _tag = _readable.readUnsignedVarint();
                int _size = _readable.readUnsignedVarint();
                switch (_tag) {
                    default:
                        this._unknownTaggedFields = _readable.readUnknownTaggedField(this._unknownTaggedFields, _tag, _size);
                        break;
                }
            }
        }
        
        @Override
        public void write(Writable _writable, ObjectSerializationCache _cache, short _version) {
            int _numTaggedFields = 0;
            _writable.writeUuid(topicId);
            {
                byte[] _stringBytes = _cache.getSerializedValue(topicName);
                _writable.writeUnsignedVarint(_stringBytes.length + 1);
                _writable.writeByteArray(_stringBytes);
            }
            _writable.writeInt(numPartitions);
            _writable.writeUnsignedVarint(partitionMetadata.size() + 1);
            for (PartitionMetadata partitionMetadataElement : partitionMetadata) {
                partitionMetadataElement.write(_writable, _cache, _version);
            }
            RawTaggedFieldWriter _rawWriter = RawTaggedFieldWriter.forFields(_unknownTaggedFields);
            _numTaggedFields += _rawWriter.numFields();
            _writable.writeUnsignedVarint(_numTaggedFields);
            _rawWriter.writeRawTags(_writable, Integer.MAX_VALUE);
        }
        
        @Override
        public void addSize(MessageSizeAccumulator _size, ObjectSerializationCache _cache, short _version) {
            int _numTaggedFields = 0;
            if (_version > 0) {
                throw new UnsupportedVersionException("Can't size version " + _version + " of TopicMetadata");
            }
            _size.addBytes(16);
            {
                byte[] _stringBytes = topicName.getBytes(StandardCharsets.UTF_8);
                if (_stringBytes.length > 0x7fff) {
                    throw new RuntimeException("'topicName' field is too long to be serialized");
                }
                _cache.cacheSerializedValue(topicName, _stringBytes);
                _size.addBytes(_stringBytes.length + ByteUtils.sizeOfUnsignedVarint(_stringBytes.length + 1));
            }
            _size.addBytes(4);
            {
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(partitionMetadata.size() + 1));
                for (PartitionMetadata partitionMetadataElement : partitionMetadata) {
                    partitionMetadataElement.addSize(_size, _cache, _version);
                }
            }
            if (_unknownTaggedFields != null) {
                _numTaggedFields += _unknownTaggedFields.size();
                for (RawTaggedField _field : _unknownTaggedFields) {
                    _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_field.tag()));
                    _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_field.size()));
                    _size.addBytes(_field.size());
                }
            }
            _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_numTaggedFields));
        }
        
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof TopicMetadata)) return false;
            TopicMetadata other = (TopicMetadata) obj;
            if (!this.topicId.equals(other.topicId)) return false;
            if (this.topicName == null) {
                if (other.topicName != null) return false;
            } else {
                if (!this.topicName.equals(other.topicName)) return false;
            }
            if (numPartitions != other.numPartitions) return false;
            if (this.partitionMetadata == null) {
                if (other.partitionMetadata != null) return false;
            } else {
                if (!this.partitionMetadata.equals(other.partitionMetadata)) return false;
            }
            return MessageUtil.compareRawTaggedFields(_unknownTaggedFields, other._unknownTaggedFields);
        }
        
        @Override
        public int hashCode() {
            int hashCode = 0;
            hashCode = 31 * hashCode + topicId.hashCode();
            hashCode = 31 * hashCode + (topicName == null ? 0 : topicName.hashCode());
            hashCode = 31 * hashCode + numPartitions;
            hashCode = 31 * hashCode + (partitionMetadata == null ? 0 : partitionMetadata.hashCode());
            return hashCode;
        }
        
        @Override
        public TopicMetadata duplicate() {
            TopicMetadata _duplicate = new TopicMetadata();
            _duplicate.topicId = topicId;
            _duplicate.topicName = topicName;
            _duplicate.numPartitions = numPartitions;
            ArrayList<PartitionMetadata> newPartitionMetadata = new ArrayList<PartitionMetadata>(partitionMetadata.size());
            for (PartitionMetadata _element : partitionMetadata) {
                newPartitionMetadata.add(_element.duplicate());
            }
            _duplicate.partitionMetadata = newPartitionMetadata;
            return _duplicate;
        }
        
        @Override
        public String toString() {
            return "TopicMetadata("
                + "topicId=" + topicId.toString()
                + ", topicName=" + ((topicName == null) ? "null" : "'" + topicName.toString() + "'")
                + ", numPartitions=" + numPartitions
                + ", partitionMetadata=" + MessageUtil.deepToString(partitionMetadata.iterator())
                + ")";
        }
        
        public Uuid topicId() {
            return this.topicId;
        }
        
        public String topicName() {
            return this.topicName;
        }
        
        public int numPartitions() {
            return this.numPartitions;
        }
        
        public List<PartitionMetadata> partitionMetadata() {
            return this.partitionMetadata;
        }
        
        @Override
        public List<RawTaggedField> unknownTaggedFields() {
            if (_unknownTaggedFields == null) {
                _unknownTaggedFields = new ArrayList<>(0);
            }
            return _unknownTaggedFields;
        }
        
        public TopicMetadata setTopicId(Uuid v) {
            this.topicId = v;
            return this;
        }
        
        public TopicMetadata setTopicName(String v) {
            this.topicName = v;
            return this;
        }
        
        public TopicMetadata setNumPartitions(int v) {
            this.numPartitions = v;
            return this;
        }
        
        public TopicMetadata setPartitionMetadata(List<PartitionMetadata> v) {
            this.partitionMetadata = v;
            return this;
        }
    }
    
    public static class PartitionMetadata implements Message {
        int partition;
        List<String> racks;
        private List<RawTaggedField> _unknownTaggedFields;
        
        public static final Schema SCHEMA_0 =
            new Schema(
                new Field("partition", Type.INT32, "The partition number."),
                new Field("racks", new CompactArrayOf(Type.COMPACT_STRING), "The set of racks that the partition is mapped to."),
                TaggedFieldsSection.of(
                )
            );
        
        public static final Schema[] SCHEMAS = new Schema[] {
            SCHEMA_0
        };
        
        public static final short LOWEST_SUPPORTED_VERSION = 0;
        public static final short HIGHEST_SUPPORTED_VERSION = 0;
        
        public PartitionMetadata(Readable _readable, short _version) {
            read(_readable, _version);
        }
        
        public PartitionMetadata() {
            this.partition = 0;
            this.racks = new ArrayList<String>(0);
        }
        
        
        @Override
        public short lowestSupportedVersion() {
            return 0;
        }
        
        @Override
        public short highestSupportedVersion() {
            return 0;
        }
        
        @Override
        public final void read(Readable _readable, short _version) {
            if (_version > 0) {
                throw new UnsupportedVersionException("Can't read version " + _version + " of PartitionMetadata");
            }
            this.partition = _readable.readInt();
            {
                int arrayLength;
                arrayLength = _readable.readUnsignedVarint() - 1;
                if (arrayLength < 0) {
                    throw new RuntimeException("non-nullable field racks was serialized as null");
                } else {
                    if (arrayLength > _readable.remaining()) {
                        throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                    }
                    ArrayList<String> newCollection = new ArrayList<>(arrayLength);
                    for (int i = 0; i < arrayLength; i++) {
                        int length;
                        length = _readable.readUnsignedVarint() - 1;
                        if (length < 0) {
                            throw new RuntimeException("non-nullable field racks element was serialized as null");
                        } else if (length > 0x7fff) {
                            throw new RuntimeException("string field racks element had invalid length " + length);
                        } else {
                            newCollection.add(_readable.readString(length));
                        }
                    }
                    this.racks = newCollection;
                }
            }
            this._unknownTaggedFields = null;
            int _numTaggedFields = _readable.readUnsignedVarint();
            for (int _i = 0; _i < _numTaggedFields; _i++) {
                int _tag = _readable.readUnsignedVarint();
                int _size = _readable.readUnsignedVarint();
                switch (_tag) {
                    default:
                        this._unknownTaggedFields = _readable.readUnknownTaggedField(this._unknownTaggedFields, _tag, _size);
                        break;
                }
            }
        }
        
        @Override
        public void write(Writable _writable, ObjectSerializationCache _cache, short _version) {
            int _numTaggedFields = 0;
            _writable.writeInt(partition);
            _writable.writeUnsignedVarint(racks.size() + 1);
            for (String racksElement : racks) {
                {
                    byte[] _stringBytes = _cache.getSerializedValue(racksElement);
                    _writable.writeUnsignedVarint(_stringBytes.length + 1);
                    _writable.writeByteArray(_stringBytes);
                }
            }
            RawTaggedFieldWriter _rawWriter = RawTaggedFieldWriter.forFields(_unknownTaggedFields);
            _numTaggedFields += _rawWriter.numFields();
            _writable.writeUnsignedVarint(_numTaggedFields);
            _rawWriter.writeRawTags(_writable, Integer.MAX_VALUE);
        }
        
        @Override
        public void addSize(MessageSizeAccumulator _size, ObjectSerializationCache _cache, short _version) {
            int _numTaggedFields = 0;
            if (_version > 0) {
                throw new UnsupportedVersionException("Can't size version " + _version + " of PartitionMetadata");
            }
            _size.addBytes(4);
            {
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(racks.size() + 1));
                for (String racksElement : racks) {
                    byte[] _stringBytes = racksElement.getBytes(StandardCharsets.UTF_8);
                    if (_stringBytes.length > 0x7fff) {
                        throw new RuntimeException("'racksElement' field is too long to be serialized");
                    }
                    _cache.cacheSerializedValue(racksElement, _stringBytes);
                    _size.addBytes(_stringBytes.length + ByteUtils.sizeOfUnsignedVarint(_stringBytes.length + 1));
                }
            }
            if (_unknownTaggedFields != null) {
                _numTaggedFields += _unknownTaggedFields.size();
                for (RawTaggedField _field : _unknownTaggedFields) {
                    _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_field.tag()));
                    _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_field.size()));
                    _size.addBytes(_field.size());
                }
            }
            _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_numTaggedFields));
        }
        
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof PartitionMetadata)) return false;
            PartitionMetadata other = (PartitionMetadata) obj;
            if (partition != other.partition) return false;
            if (this.racks == null) {
                if (other.racks != null) return false;
            } else {
                if (!this.racks.equals(other.racks)) return false;
            }
            return MessageUtil.compareRawTaggedFields(_unknownTaggedFields, other._unknownTaggedFields);
        }
        
        @Override
        public int hashCode() {
            int hashCode = 0;
            hashCode = 31 * hashCode + partition;
            hashCode = 31 * hashCode + (racks == null ? 0 : racks.hashCode());
            return hashCode;
        }
        
        @Override
        public PartitionMetadata duplicate() {
            PartitionMetadata _duplicate = new PartitionMetadata();
            _duplicate.partition = partition;
            ArrayList<String> newRacks = new ArrayList<String>(racks.size());
            for (String _element : racks) {
                newRacks.add(_element);
            }
            _duplicate.racks = newRacks;
            return _duplicate;
        }
        
        @Override
        public String toString() {
            return "PartitionMetadata("
                + "partition=" + partition
                + ", racks=" + MessageUtil.deepToString(racks.iterator())
                + ")";
        }
        
        public int partition() {
            return this.partition;
        }
        
        public List<String> racks() {
            return this.racks;
        }
        
        @Override
        public List<RawTaggedField> unknownTaggedFields() {
            if (_unknownTaggedFields == null) {
                _unknownTaggedFields = new ArrayList<>(0);
            }
            return _unknownTaggedFields;
        }
        
        public PartitionMetadata setPartition(int v) {
            this.partition = v;
            return this;
        }
        
        public PartitionMetadata setRacks(List<String> v) {
            this.racks = v;
            return this;
        }
    }
}
