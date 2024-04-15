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


public class ConsumerGroupCurrentMemberAssignmentValue implements ApiMessage {
    int memberEpoch;
    int previousMemberEpoch;
    int targetMemberEpoch;
    List<TopicPartitions> assignedPartitions;
    List<TopicPartitions> partitionsPendingRevocation;
    List<TopicPartitions> partitionsPendingAssignment;
    byte error;
    short metadataVersion;
    byte[] metadataBytes;
    private List<RawTaggedField> _unknownTaggedFields;
    
    public static final Schema SCHEMA_0 =
        new Schema(
            new Field("member_epoch", Type.INT32, "The current member epoch that is expected from the member in the heartbeat request."),
            new Field("previous_member_epoch", Type.INT32, "If the last epoch bump is lost before reaching the member, the member will retry with the previous epoch."),
            new Field("target_member_epoch", Type.INT32, "The target epoch corresponding to the assignment used to compute the AssignedPartitions, the PartitionsPendingRevocation and the PartitionsPendingAssignment fields."),
            new Field("assigned_partitions", new CompactArrayOf(TopicPartitions.SCHEMA_0), "The partitions assigned to (or owned by) this member."),
            new Field("partitions_pending_revocation", new CompactArrayOf(TopicPartitions.SCHEMA_0), "The partitions that must be revoked by this member."),
            new Field("partitions_pending_assignment", new CompactArrayOf(TopicPartitions.SCHEMA_0), "The partitions that will be assigned to this member when they are freed up by their current owners."),
            new Field("error", Type.INT8, "The error reported by the assignor."),
            new Field("metadata_version", Type.INT16, "The version of the metadata bytes."),
            new Field("metadata_bytes", Type.COMPACT_BYTES, "The metadata bytes."),
            TaggedFieldsSection.of(
            )
        );
    
    public static final Schema[] SCHEMAS = new Schema[] {
        SCHEMA_0
    };
    
    public static final short LOWEST_SUPPORTED_VERSION = 0;
    public static final short HIGHEST_SUPPORTED_VERSION = 0;
    
    public ConsumerGroupCurrentMemberAssignmentValue(Readable _readable, short _version) {
        read(_readable, _version);
    }
    
    public ConsumerGroupCurrentMemberAssignmentValue() {
        this.memberEpoch = 0;
        this.previousMemberEpoch = 0;
        this.targetMemberEpoch = 0;
        this.assignedPartitions = new ArrayList<TopicPartitions>(0);
        this.partitionsPendingRevocation = new ArrayList<TopicPartitions>(0);
        this.partitionsPendingAssignment = new ArrayList<TopicPartitions>(0);
        this.error = (byte) 0;
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
        this.memberEpoch = _readable.readInt();
        this.previousMemberEpoch = _readable.readInt();
        this.targetMemberEpoch = _readable.readInt();
        {
            int arrayLength;
            arrayLength = _readable.readUnsignedVarint() - 1;
            if (arrayLength < 0) {
                throw new RuntimeException("non-nullable field assignedPartitions was serialized as null");
            } else {
                if (arrayLength > _readable.remaining()) {
                    throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                }
                ArrayList<TopicPartitions> newCollection = new ArrayList<>(arrayLength);
                for (int i = 0; i < arrayLength; i++) {
                    newCollection.add(new TopicPartitions(_readable, _version));
                }
                this.assignedPartitions = newCollection;
            }
        }
        {
            int arrayLength;
            arrayLength = _readable.readUnsignedVarint() - 1;
            if (arrayLength < 0) {
                throw new RuntimeException("non-nullable field partitionsPendingRevocation was serialized as null");
            } else {
                if (arrayLength > _readable.remaining()) {
                    throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                }
                ArrayList<TopicPartitions> newCollection = new ArrayList<>(arrayLength);
                for (int i = 0; i < arrayLength; i++) {
                    newCollection.add(new TopicPartitions(_readable, _version));
                }
                this.partitionsPendingRevocation = newCollection;
            }
        }
        {
            int arrayLength;
            arrayLength = _readable.readUnsignedVarint() - 1;
            if (arrayLength < 0) {
                throw new RuntimeException("non-nullable field partitionsPendingAssignment was serialized as null");
            } else {
                if (arrayLength > _readable.remaining()) {
                    throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                }
                ArrayList<TopicPartitions> newCollection = new ArrayList<>(arrayLength);
                for (int i = 0; i < arrayLength; i++) {
                    newCollection.add(new TopicPartitions(_readable, _version));
                }
                this.partitionsPendingAssignment = newCollection;
            }
        }
        this.error = _readable.readByte();
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
        _writable.writeInt(memberEpoch);
        _writable.writeInt(previousMemberEpoch);
        _writable.writeInt(targetMemberEpoch);
        _writable.writeUnsignedVarint(assignedPartitions.size() + 1);
        for (TopicPartitions assignedPartitionsElement : assignedPartitions) {
            assignedPartitionsElement.write(_writable, _cache, _version);
        }
        _writable.writeUnsignedVarint(partitionsPendingRevocation.size() + 1);
        for (TopicPartitions partitionsPendingRevocationElement : partitionsPendingRevocation) {
            partitionsPendingRevocationElement.write(_writable, _cache, _version);
        }
        _writable.writeUnsignedVarint(partitionsPendingAssignment.size() + 1);
        for (TopicPartitions partitionsPendingAssignmentElement : partitionsPendingAssignment) {
            partitionsPendingAssignmentElement.write(_writable, _cache, _version);
        }
        _writable.writeByte(error);
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
        _size.addBytes(4);
        _size.addBytes(4);
        _size.addBytes(4);
        {
            _size.addBytes(ByteUtils.sizeOfUnsignedVarint(assignedPartitions.size() + 1));
            for (TopicPartitions assignedPartitionsElement : assignedPartitions) {
                assignedPartitionsElement.addSize(_size, _cache, _version);
            }
        }
        {
            _size.addBytes(ByteUtils.sizeOfUnsignedVarint(partitionsPendingRevocation.size() + 1));
            for (TopicPartitions partitionsPendingRevocationElement : partitionsPendingRevocation) {
                partitionsPendingRevocationElement.addSize(_size, _cache, _version);
            }
        }
        {
            _size.addBytes(ByteUtils.sizeOfUnsignedVarint(partitionsPendingAssignment.size() + 1));
            for (TopicPartitions partitionsPendingAssignmentElement : partitionsPendingAssignment) {
                partitionsPendingAssignmentElement.addSize(_size, _cache, _version);
            }
        }
        _size.addBytes(1);
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
        if (!(obj instanceof ConsumerGroupCurrentMemberAssignmentValue)) return false;
        ConsumerGroupCurrentMemberAssignmentValue other = (ConsumerGroupCurrentMemberAssignmentValue) obj;
        if (memberEpoch != other.memberEpoch) return false;
        if (previousMemberEpoch != other.previousMemberEpoch) return false;
        if (targetMemberEpoch != other.targetMemberEpoch) return false;
        if (this.assignedPartitions == null) {
            if (other.assignedPartitions != null) return false;
        } else {
            if (!this.assignedPartitions.equals(other.assignedPartitions)) return false;
        }
        if (this.partitionsPendingRevocation == null) {
            if (other.partitionsPendingRevocation != null) return false;
        } else {
            if (!this.partitionsPendingRevocation.equals(other.partitionsPendingRevocation)) return false;
        }
        if (this.partitionsPendingAssignment == null) {
            if (other.partitionsPendingAssignment != null) return false;
        } else {
            if (!this.partitionsPendingAssignment.equals(other.partitionsPendingAssignment)) return false;
        }
        if (error != other.error) return false;
        if (metadataVersion != other.metadataVersion) return false;
        if (!Arrays.equals(this.metadataBytes, other.metadataBytes)) return false;
        return MessageUtil.compareRawTaggedFields(_unknownTaggedFields, other._unknownTaggedFields);
    }
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode = 31 * hashCode + memberEpoch;
        hashCode = 31 * hashCode + previousMemberEpoch;
        hashCode = 31 * hashCode + targetMemberEpoch;
        hashCode = 31 * hashCode + (assignedPartitions == null ? 0 : assignedPartitions.hashCode());
        hashCode = 31 * hashCode + (partitionsPendingRevocation == null ? 0 : partitionsPendingRevocation.hashCode());
        hashCode = 31 * hashCode + (partitionsPendingAssignment == null ? 0 : partitionsPendingAssignment.hashCode());
        hashCode = 31 * hashCode + error;
        hashCode = 31 * hashCode + metadataVersion;
        hashCode = 31 * hashCode + Arrays.hashCode(metadataBytes);
        return hashCode;
    }
    
    @Override
    public ConsumerGroupCurrentMemberAssignmentValue duplicate() {
        ConsumerGroupCurrentMemberAssignmentValue _duplicate = new ConsumerGroupCurrentMemberAssignmentValue();
        _duplicate.memberEpoch = memberEpoch;
        _duplicate.previousMemberEpoch = previousMemberEpoch;
        _duplicate.targetMemberEpoch = targetMemberEpoch;
        ArrayList<TopicPartitions> newAssignedPartitions = new ArrayList<TopicPartitions>(assignedPartitions.size());
        for (TopicPartitions _element : assignedPartitions) {
            newAssignedPartitions.add(_element.duplicate());
        }
        _duplicate.assignedPartitions = newAssignedPartitions;
        ArrayList<TopicPartitions> newPartitionsPendingRevocation = new ArrayList<TopicPartitions>(partitionsPendingRevocation.size());
        for (TopicPartitions _element : partitionsPendingRevocation) {
            newPartitionsPendingRevocation.add(_element.duplicate());
        }
        _duplicate.partitionsPendingRevocation = newPartitionsPendingRevocation;
        ArrayList<TopicPartitions> newPartitionsPendingAssignment = new ArrayList<TopicPartitions>(partitionsPendingAssignment.size());
        for (TopicPartitions _element : partitionsPendingAssignment) {
            newPartitionsPendingAssignment.add(_element.duplicate());
        }
        _duplicate.partitionsPendingAssignment = newPartitionsPendingAssignment;
        _duplicate.error = error;
        _duplicate.metadataVersion = metadataVersion;
        _duplicate.metadataBytes = MessageUtil.duplicate(metadataBytes);
        return _duplicate;
    }
    
    @Override
    public String toString() {
        return "ConsumerGroupCurrentMemberAssignmentValue("
            + "memberEpoch=" + memberEpoch
            + ", previousMemberEpoch=" + previousMemberEpoch
            + ", targetMemberEpoch=" + targetMemberEpoch
            + ", assignedPartitions=" + MessageUtil.deepToString(assignedPartitions.iterator())
            + ", partitionsPendingRevocation=" + MessageUtil.deepToString(partitionsPendingRevocation.iterator())
            + ", partitionsPendingAssignment=" + MessageUtil.deepToString(partitionsPendingAssignment.iterator())
            + ", error=" + error
            + ", metadataVersion=" + metadataVersion
            + ", metadataBytes=" + Arrays.toString(metadataBytes)
            + ")";
    }
    
    public int memberEpoch() {
        return this.memberEpoch;
    }
    
    public int previousMemberEpoch() {
        return this.previousMemberEpoch;
    }
    
    public int targetMemberEpoch() {
        return this.targetMemberEpoch;
    }
    
    public List<TopicPartitions> assignedPartitions() {
        return this.assignedPartitions;
    }
    
    public List<TopicPartitions> partitionsPendingRevocation() {
        return this.partitionsPendingRevocation;
    }
    
    public List<TopicPartitions> partitionsPendingAssignment() {
        return this.partitionsPendingAssignment;
    }
    
    public byte error() {
        return this.error;
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
    
    public ConsumerGroupCurrentMemberAssignmentValue setMemberEpoch(int v) {
        this.memberEpoch = v;
        return this;
    }
    
    public ConsumerGroupCurrentMemberAssignmentValue setPreviousMemberEpoch(int v) {
        this.previousMemberEpoch = v;
        return this;
    }
    
    public ConsumerGroupCurrentMemberAssignmentValue setTargetMemberEpoch(int v) {
        this.targetMemberEpoch = v;
        return this;
    }
    
    public ConsumerGroupCurrentMemberAssignmentValue setAssignedPartitions(List<TopicPartitions> v) {
        this.assignedPartitions = v;
        return this;
    }
    
    public ConsumerGroupCurrentMemberAssignmentValue setPartitionsPendingRevocation(List<TopicPartitions> v) {
        this.partitionsPendingRevocation = v;
        return this;
    }
    
    public ConsumerGroupCurrentMemberAssignmentValue setPartitionsPendingAssignment(List<TopicPartitions> v) {
        this.partitionsPendingAssignment = v;
        return this;
    }
    
    public ConsumerGroupCurrentMemberAssignmentValue setError(byte v) {
        this.error = v;
        return this;
    }
    
    public ConsumerGroupCurrentMemberAssignmentValue setMetadataVersion(short v) {
        this.metadataVersion = v;
        return this;
    }
    
    public ConsumerGroupCurrentMemberAssignmentValue setMetadataBytes(byte[] v) {
        this.metadataBytes = v;
        return this;
    }
    
    public static class TopicPartitions implements Message {
        Uuid topicId;
        List<Integer> partitions;
        private List<RawTaggedField> _unknownTaggedFields;
        
        public static final Schema SCHEMA_0 =
            new Schema(
                new Field("topic_id", Type.UUID, "The topic Id."),
                new Field("partitions", new CompactArrayOf(Type.INT32), "The partition Ids."),
                TaggedFieldsSection.of(
                )
            );
        
        public static final Schema[] SCHEMAS = new Schema[] {
            SCHEMA_0
        };
        
        public static final short LOWEST_SUPPORTED_VERSION = 0;
        public static final short HIGHEST_SUPPORTED_VERSION = 0;
        
        public TopicPartitions(Readable _readable, short _version) {
            read(_readable, _version);
        }
        
        public TopicPartitions() {
            this.topicId = Uuid.ZERO_UUID;
            this.partitions = new ArrayList<Integer>(0);
        }
        
        
        @Override
        public short lowestSupportedVersion() {
            return 0;
        }
        
        @Override
        public short highestSupportedVersion() {
            return 32767;
        }
        
        @Override
        public final void read(Readable _readable, short _version) {
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
            if (!(obj instanceof TopicPartitions)) return false;
            TopicPartitions other = (TopicPartitions) obj;
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
        public TopicPartitions duplicate() {
            TopicPartitions _duplicate = new TopicPartitions();
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
            return "TopicPartitions("
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
        
        public TopicPartitions setTopicId(Uuid v) {
            this.topicId = v;
            return this;
        }
        
        public TopicPartitions setPartitions(List<Integer> v) {
            this.partitions = v;
            return this;
        }
    }
}
