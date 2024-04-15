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
import org.apache.kafka.common.errors.UnsupportedVersionException;
import org.apache.kafka.common.protocol.ApiMessage;
import org.apache.kafka.common.protocol.MessageSizeAccumulator;
import org.apache.kafka.common.protocol.MessageUtil;
import org.apache.kafka.common.protocol.ObjectSerializationCache;
import org.apache.kafka.common.protocol.Readable;
import org.apache.kafka.common.protocol.Writable;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.protocol.types.RawTaggedField;
import org.apache.kafka.common.protocol.types.RawTaggedFieldWriter;
import org.apache.kafka.common.protocol.types.Schema;
import org.apache.kafka.common.protocol.types.Type;
import org.apache.kafka.common.utils.ByteUtils;


public class ConsumerGroupMemberMetadataKey implements ApiMessage {
    String groupId;
    String memberId;
    private List<RawTaggedField> _unknownTaggedFields;
    
    public static final Schema SCHEMA_5 =
        new Schema(
            new Field("group_id", Type.STRING, "The group id."),
            new Field("member_id", Type.STRING, "The member id.")
        );
    
    public static final Schema[] SCHEMAS = new Schema[] {
        null,
        null,
        null,
        null,
        null,
        SCHEMA_5
    };
    
    public static final short LOWEST_SUPPORTED_VERSION = 5;
    public static final short HIGHEST_SUPPORTED_VERSION = 5;
    
    public ConsumerGroupMemberMetadataKey(Readable _readable, short _version) {
        read(_readable, _version);
    }
    
    public ConsumerGroupMemberMetadataKey() {
        this.groupId = "";
        this.memberId = "";
    }
    
    @Override
    public short apiKey() {
        return -1;
    }
    
    @Override
    public short lowestSupportedVersion() {
        return 5;
    }
    
    @Override
    public short highestSupportedVersion() {
        return 5;
    }
    
    @Override
    public final void read(Readable _readable, short _version) {
        {
            int length;
            length = _readable.readShort();
            if (length < 0) {
                throw new RuntimeException("non-nullable field groupId was serialized as null");
            } else if (length > 0x7fff) {
                throw new RuntimeException("string field groupId had invalid length " + length);
            } else {
                this.groupId = _readable.readString(length);
            }
        }
        {
            int length;
            length = _readable.readShort();
            if (length < 0) {
                throw new RuntimeException("non-nullable field memberId was serialized as null");
            } else if (length > 0x7fff) {
                throw new RuntimeException("string field memberId had invalid length " + length);
            } else {
                this.memberId = _readable.readString(length);
            }
        }
        this._unknownTaggedFields = null;
    }
    
    @Override
    public void write(Writable _writable, ObjectSerializationCache _cache, short _version) {
        int _numTaggedFields = 0;
        {
            byte[] _stringBytes = _cache.getSerializedValue(groupId);
            _writable.writeShort((short) _stringBytes.length);
            _writable.writeByteArray(_stringBytes);
        }
        {
            byte[] _stringBytes = _cache.getSerializedValue(memberId);
            _writable.writeShort((short) _stringBytes.length);
            _writable.writeByteArray(_stringBytes);
        }
        RawTaggedFieldWriter _rawWriter = RawTaggedFieldWriter.forFields(_unknownTaggedFields);
        _numTaggedFields += _rawWriter.numFields();
        if (_numTaggedFields > 0) {
            throw new UnsupportedVersionException("Tagged fields were set, but version " + _version + " of this message does not support them.");
        }
    }
    
    @Override
    public void addSize(MessageSizeAccumulator _size, ObjectSerializationCache _cache, short _version) {
        int _numTaggedFields = 0;
        {
            byte[] _stringBytes = groupId.getBytes(StandardCharsets.UTF_8);
            if (_stringBytes.length > 0x7fff) {
                throw new RuntimeException("'groupId' field is too long to be serialized");
            }
            _cache.cacheSerializedValue(groupId, _stringBytes);
            _size.addBytes(_stringBytes.length + 2);
        }
        {
            byte[] _stringBytes = memberId.getBytes(StandardCharsets.UTF_8);
            if (_stringBytes.length > 0x7fff) {
                throw new RuntimeException("'memberId' field is too long to be serialized");
            }
            _cache.cacheSerializedValue(memberId, _stringBytes);
            _size.addBytes(_stringBytes.length + 2);
        }
        if (_unknownTaggedFields != null) {
            _numTaggedFields += _unknownTaggedFields.size();
            for (RawTaggedField _field : _unknownTaggedFields) {
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_field.tag()));
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_field.size()));
                _size.addBytes(_field.size());
            }
        }
        if (_numTaggedFields > 0) {
            throw new UnsupportedVersionException("Tagged fields were set, but version " + _version + " of this message does not support them.");
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ConsumerGroupMemberMetadataKey)) return false;
        ConsumerGroupMemberMetadataKey other = (ConsumerGroupMemberMetadataKey) obj;
        if (this.groupId == null) {
            if (other.groupId != null) return false;
        } else {
            if (!this.groupId.equals(other.groupId)) return false;
        }
        if (this.memberId == null) {
            if (other.memberId != null) return false;
        } else {
            if (!this.memberId.equals(other.memberId)) return false;
        }
        return MessageUtil.compareRawTaggedFields(_unknownTaggedFields, other._unknownTaggedFields);
    }
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode = 31 * hashCode + (groupId == null ? 0 : groupId.hashCode());
        hashCode = 31 * hashCode + (memberId == null ? 0 : memberId.hashCode());
        return hashCode;
    }
    
    @Override
    public ConsumerGroupMemberMetadataKey duplicate() {
        ConsumerGroupMemberMetadataKey _duplicate = new ConsumerGroupMemberMetadataKey();
        _duplicate.groupId = groupId;
        _duplicate.memberId = memberId;
        return _duplicate;
    }
    
    @Override
    public String toString() {
        return "ConsumerGroupMemberMetadataKey("
            + "groupId=" + ((groupId == null) ? "null" : "'" + groupId.toString() + "'")
            + ", memberId=" + ((memberId == null) ? "null" : "'" + memberId.toString() + "'")
            + ")";
    }
    
    public String groupId() {
        return this.groupId;
    }
    
    public String memberId() {
        return this.memberId;
    }
    
    @Override
    public List<RawTaggedField> unknownTaggedFields() {
        if (_unknownTaggedFields == null) {
            _unknownTaggedFields = new ArrayList<>(0);
        }
        return _unknownTaggedFields;
    }
    
    public ConsumerGroupMemberMetadataKey setGroupId(String v) {
        this.groupId = v;
        return this;
    }
    
    public ConsumerGroupMemberMetadataKey setMemberId(String v) {
        this.memberId = v;
        return this;
    }
}
