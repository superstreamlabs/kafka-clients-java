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

import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.kafka.common.utils.Bytes;

import static org.apache.kafka.common.protocol.types.Field.TaggedFieldsSection;


public class ConsumerGroupTargetAssignmentMemberValue implements ApiMessage {
    byte error;
    List<TopicPartition> topicPartitions;
    short metadataVersion;
    byte[] metadataBytes;
    private List<RawTaggedField> _unknownTaggedFields;
    
    public static final Schema SCHEMA_0 =
        new Schema(
            new Field("error", Type.INT8, "The assigned error."),
            new Field("topic_partitions", new CompactArrayOf(TopicPartition.SCHEMA_0), "The assigned partitions."),
            new Field("metadata_version", Type.INT16, "The version of the assigned metadata."),
            new Field("metadata_bytes", Type.COMPACT_BYTES, "The assigned metadata."),
            TaggedFieldsSection.of(
            )
        );
    
    public static final Schema[] SCHEMAS = new Schema[] {
        SCHEMA_0
    };
    
    public static final short LOWEST_SUPPORTED_VERSION = 0;
    public static final short HIGHEST_SUPPORTED_VERSION = 0;
    
    public ConsumerGroupTargetAssignmentMemberValue(Readable _readable, short _version) {
        read(_readable, _version);
    }
    
    public ConsumerGroupTargetAssignmentMemberValue() {
        this.error = (byte) 0;
        this.topicPartitions = new ArrayList<TopicPartition>(0);
        this.metadataVersion = (short) 0;
        this.metadataBytes = Bytes.EMPTY;
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
        this.error = _readable.readByte();
        {
            int arrayLength;
            arrayLength = _readable.readUnsignedVarint() - 1;
            if (arrayLength < 0) {
                throw new RuntimeException("non-nullable field topicPartitions was serialized as null");
            } else {
                if (arrayLength > _readable.remaining()) {
                    throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                }
                ArrayList<TopicPartition> newCollection = new ArrayList<>(arrayLength);
                for (int i = 0; i < arrayLength; i++) {
                    newCollection.add(new TopicPartition(_readable, _version));
                }
                this.topicPartitions = newCollection;
            }
        }
        this.metadataVersion = _readable.readShort();
        {
            int length;
            length = _readable.readUnsignedVarint() - 1;
            if (length < 0) {
                throw new RuntimeException("non-nullable field metadataBytes was serialized as null");
            } else {
                byte[] newBytes = _readable.readArray(length);
                this.metadataBytes = newBytes;
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
        _writable.writeByte(error);
        _writable.writeUnsignedVarint(topicPartitions.size() + 1);
        for (TopicPartition topicPartitionsElement : topicPartitions) {
            topicPartitionsElement.write(_writable, _cache, _version);
        }
        _writable.writeShort(metadataVersion);
        _writable.writeUnsignedVarint(metadataBytes.length + 1);
        _writable.writeByteArray(metadataBytes);
        RawTaggedFieldWriter _rawWriter = RawTaggedFieldWriter.forFields(_unknownTaggedFields);
        _numTaggedFields += _rawWriter.numFields();
        _writable.writeUnsignedVarint(_numTaggedFields);
        _rawWriter.writeRawTags(_writable, Integer.MAX_VALUE);
    }
    
    @Override
    public void addSize(MessageSizeAccumulator _size, ObjectSerializationCache _cache, short _version) {
        int _numTaggedFields = 0;
        _size.addBytes(1);
        {
            _size.addBytes(ByteUtils.sizeOfUnsignedVarint(topicPartitions.size() + 1));
            for (TopicPartition topicPartitionsElement : topicPartitions) {
                topicPartitionsElement.addSize(_size, _cache, _version);
            }
        }
        _size.addBytes(2);
        {
            _size.addBytes(metadataBytes.length);
            _size.addBytes(ByteUtils.sizeOfUnsignedVarint(metadataBytes.length + 1));
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
        if (!(obj instanceof ConsumerGroupTargetAssignmentMemberValue)) return false;
        ConsumerGroupTargetAssignmentMemberValue other = (ConsumerGroupTargetAssignmentMemberValue) obj;
        if (error != other.error) return false;
        if (this.topicPartitions == null) {
            if (other.topicPartitions != null) return false;
        } else {
            if (!this.topicPartitions.equals(other.topicPartitions)) return false;
        }
        if (metadataVersion != other.metadataVersion) return false;
        if (!Arrays.equals(this.metadataBytes, other.metadataBytes)) return false;
        return MessageUtil.compareRawTaggedFields(_unknownTaggedFields, other._unknownTaggedFields);
    }
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode = 31 * hashCode + error;
        hashCode = 31 * hashCode + (topicPartitions == null ? 0 : topicPartitions.hashCode());
        hashCode = 31 * hashCode + metadataVersion;
        hashCode = 31 * hashCode + Arrays.hashCode(metadataBytes);
        return hashCode;
    }
    
    @Override
    public ConsumerGroupTargetAssignmentMemberValue duplicate() {
        ConsumerGroupTargetAssignmentMemberValue _duplicate = new ConsumerGroupTargetAssignmentMemberValue();
        _duplicate.error = error;
        ArrayList<TopicPartition> newTopicPartitions = new ArrayList<TopicPartition>(topicPartitions.size());
        for (TopicPartition _element : topicPartitions) {
            newTopicPartitions.add(_element.duplicate());
        }
        _duplicate.topicPartitions = newTopicPartitions;
        _duplicate.metadataVersion = metadataVersion;
        _duplicate.metadataBytes = MessageUtil.duplicate(metadataBytes);
        return _duplicate;
    }
    
    @Override
    public String toString() {
        return "ConsumerGroupTargetAssignmentMemberValue("
            + "error=" + error
            + ", topicPartitions=" + MessageUtil.deepToString(topicPartitions.iterator())
            + ", metadataVersion=" + metadataVersion
            + ", metadataBytes=" + Arrays.toString(metadataBytes)
            + ")";
    }
    
    public byte error() {
        return this.error;
    }
    
    public List<TopicPartition> topicPartitions() {
        return this.topicPartitions;
    }
    
    public short metadataVersion() {
        return this.metadataVersion;
    }
    
    public byte[] metadataBytes() {
        return this.metadataBytes;
    }
    
    @Override
    public List<RawTaggedField> unknownTaggedFields() {
        if (_unknownTaggedFields == null) {
            _unknownTaggedFields = new ArrayList<>(0);
        }
        return _unknownTaggedFields;
    }
    
    public ConsumerGroupTargetAssignmentMemberValue setError(byte v) {
        this.error = v;
        return this;
    }
    
    public ConsumerGroupTargetAssignmentMemberValue setTopicPartitions(List<TopicPartition> v) {
        this.topicPartitions = v;
        return this;
    }
    
    public ConsumerGroupTargetAssignmentMemberValue setMetadataVersion(short v) {
        this.metadataVersion = v;
        return this;
    }
    
    public ConsumerGroupTargetAssignmentMemberValue setMetadataBytes(byte[] v) {
        this.metadataBytes = v;
        return this;
    }
    
    public static class TopicPartition implements Message {
        Uuid topicId;
        List<Integer> partitions;
        private List<RawTaggedField> _unknownTaggedFields;
        
        public static final Schema SCHEMA_0 =
            new Schema(
                new Field("topic_id", Type.UUID, ""),
                new Field("partitions", new CompactArrayOf(Type.INT32), ""),
                TaggedFieldsSection.of(
                )
            );
        
        public static final Schema[] SCHEMAS = new Schema[] {
            SCHEMA_0
        };
        
        public static final short LOWEST_SUPPORTED_VERSION = 0;
        public static final short HIGHEST_SUPPORTED_VERSION = 0;
        
        public TopicPartition(Readable _readable, short _version) {
            read(_readable, _version);
        }
        
        public TopicPartition() {
            this.topicId = Uuid.ZERO_UUID;
            this.partitions = new ArrayList<Integer>(0);
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
                throw new UnsupportedVersionException("Can't read version " + _version + " of TopicPartition");
            }
            this.topicId = _readable.readUuid();
            {
                int arrayLength;
                arrayLength = _readable.readUnsignedVarint() - 1;
                if (arrayLength < 0) {
                    throw new RuntimeException("non-nullable field partitions was serialized as null");
                } else {
                    if (arrayLength > _readable.remaining()) {
                        throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                    }
                    ArrayList<Integer> newCollection = new ArrayList<>(arrayLength);
                    for (int i = 0; i < arrayLength; i++) {
                        newCollection.add(_readable.readInt());
                    }
                    this.partitions = newCollection;
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
            _writable.writeUnsignedVarint(partitions.size() + 1);
            for (Integer partitionsElement : partitions) {
                _writable.writeInt(partitionsElement);
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
                throw new UnsupportedVersionException("Can't size version " + _version + " of TopicPartition");
            }
            _size.addBytes(16);
            {
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(partitions.size() + 1));
                _size.addBytes(partitions.size() * 4);
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
            if (!(obj instanceof TopicPartition)) return false;
            TopicPartition other = (TopicPartition) obj;
            if (!this.topicId.equals(other.topicId)) return false;
            if (this.partitions == null) {
                if (other.partitions != null) return false;
            } else {
                if (!this.partitions.equals(other.partitions)) return false;
            }
            return MessageUtil.compareRawTaggedFields(_unknownTaggedFields, other._unknownTaggedFields);
        }
        
        @Override
        public int hashCode() {
            int hashCode = 0;
            hashCode = 31 * hashCode + topicId.hashCode();
            hashCode = 31 * hashCode + (partitions == null ? 0 : partitions.hashCode());
            return hashCode;
        }
        
        @Override
        public TopicPartition duplicate() {
            TopicPartition _duplicate = new TopicPartition();
            _duplicate.topicId = topicId;
            ArrayList<Integer> newPartitions = new ArrayList<Integer>(partitions.size());
            for (Integer _element : partitions) {
                newPartitions.add(_element);
            }
            _duplicate.partitions = newPartitions;
            return _duplicate;
        }
        
        @Override
        public String toString() {
            return "TopicPartition("
                + "topicId=" + topicId.toString()
                + ", partitions=" + MessageUtil.deepToString(partitions.iterator())
                + ")";
        }
        
        public Uuid topicId() {
            return this.topicId;
        }
        
        public List<Integer> partitions() {
            return this.partitions;
        }
        
        @Override
        public List<RawTaggedField> unknownTaggedFields() {
            if (_unknownTaggedFields == null) {
                _unknownTaggedFields = new ArrayList<>(0);
            }
            return _unknownTaggedFields;
        }
        
        public TopicPartition setTopicId(Uuid v) {
            this.topicId = v;
            return this;
        }
        
        public TopicPartition setPartitions(List<Integer> v) {
            this.partitions = v;
            return this;
        }
    }
}
