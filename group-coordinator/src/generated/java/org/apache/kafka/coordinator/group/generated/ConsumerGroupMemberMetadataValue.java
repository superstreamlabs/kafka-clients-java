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
import java.util.Arrays;
import java.util.List;
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


public class ConsumerGroupMemberMetadataValue implements ApiMessage {
    String instanceId;
    String rackId;
    String clientId;
    String clientHost;
    List<String> subscribedTopicNames;
    String subscribedTopicRegex;
    int rebalanceTimeoutMs;
    String serverAssignor;
    List<Assignor> assignors;
    private List<RawTaggedField> _unknownTaggedFields;
    
    public static final Schema SCHEMA_0 =
        new Schema(
            new Field("instance_id", Type.COMPACT_NULLABLE_STRING, "The (optional) instance id."),
            new Field("rack_id", Type.COMPACT_NULLABLE_STRING, "The (optional) rack id."),
            new Field("client_id", Type.COMPACT_STRING, "The client id."),
            new Field("client_host", Type.COMPACT_STRING, "The client host."),
            new Field("subscribed_topic_names", new CompactArrayOf(Type.COMPACT_STRING), "The list of subscribed topic names."),
            new Field("subscribed_topic_regex", Type.COMPACT_NULLABLE_STRING, "The subscribed topic regular expression."),
            new Field("rebalance_timeout_ms", Type.INT32, "The rebalance timeout"),
            new Field("server_assignor", Type.COMPACT_NULLABLE_STRING, "The server assignor to use; or null if not used."),
            new Field("assignors", new CompactArrayOf(Assignor.SCHEMA_0), "The list of assignors."),
            TaggedFieldsSection.of(
            )
        );
    
    public static final Schema[] SCHEMAS = new Schema[] {
        SCHEMA_0
    };
    
    public static final short LOWEST_SUPPORTED_VERSION = 0;
    public static final short HIGHEST_SUPPORTED_VERSION = 0;
    
    public ConsumerGroupMemberMetadataValue(Readable _readable, short _version) {
        read(_readable, _version);
    }
    
    public ConsumerGroupMemberMetadataValue() {
        this.instanceId = "";
        this.rackId = "";
        this.clientId = "";
        this.clientHost = "";
        this.subscribedTopicNames = new ArrayList<String>(0);
        this.subscribedTopicRegex = "";
        this.rebalanceTimeoutMs = -1;
        this.serverAssignor = "";
        this.assignors = new ArrayList<Assignor>(0);
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
        {
            int length;
            length = _readable.readUnsignedVarint() - 1;
            if (length < 0) {
                this.instanceId = null;
            } else if (length > 0x7fff) {
                throw new RuntimeException("string field instanceId had invalid length " + length);
            } else {
                this.instanceId = _readable.readString(length);
            }
        }
        {
            int length;
            length = _readable.readUnsignedVarint() - 1;
            if (length < 0) {
                this.rackId = null;
            } else if (length > 0x7fff) {
                throw new RuntimeException("string field rackId had invalid length " + length);
            } else {
                this.rackId = _readable.readString(length);
            }
        }
        {
            int length;
            length = _readable.readUnsignedVarint() - 1;
            if (length < 0) {
                throw new RuntimeException("non-nullable field clientId was serialized as null");
            } else if (length > 0x7fff) {
                throw new RuntimeException("string field clientId had invalid length " + length);
            } else {
                this.clientId = _readable.readString(length);
            }
        }
        {
            int length;
            length = _readable.readUnsignedVarint() - 1;
            if (length < 0) {
                throw new RuntimeException("non-nullable field clientHost was serialized as null");
            } else if (length > 0x7fff) {
                throw new RuntimeException("string field clientHost had invalid length " + length);
            } else {
                this.clientHost = _readable.readString(length);
            }
        }
        {
            int arrayLength;
            arrayLength = _readable.readUnsignedVarint() - 1;
            if (arrayLength < 0) {
                throw new RuntimeException("non-nullable field subscribedTopicNames was serialized as null");
            } else {
                if (arrayLength > _readable.remaining()) {
                    throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                }
                ArrayList<String> newCollection = new ArrayList<>(arrayLength);
                for (int i = 0; i < arrayLength; i++) {
                    int length;
                    length = _readable.readUnsignedVarint() - 1;
                    if (length < 0) {
                        throw new RuntimeException("non-nullable field subscribedTopicNames element was serialized as null");
                    } else if (length > 0x7fff) {
                        throw new RuntimeException("string field subscribedTopicNames element had invalid length " + length);
                    } else {
                        newCollection.add(_readable.readString(length));
                    }
                }
                this.subscribedTopicNames = newCollection;
            }
        }
        {
            int length;
            length = _readable.readUnsignedVarint() - 1;
            if (length < 0) {
                this.subscribedTopicRegex = null;
            } else if (length > 0x7fff) {
                throw new RuntimeException("string field subscribedTopicRegex had invalid length " + length);
            } else {
                this.subscribedTopicRegex = _readable.readString(length);
            }
        }
        this.rebalanceTimeoutMs = _readable.readInt();
        {
            int length;
            length = _readable.readUnsignedVarint() - 1;
            if (length < 0) {
                this.serverAssignor = null;
            } else if (length > 0x7fff) {
                throw new RuntimeException("string field serverAssignor had invalid length " + length);
            } else {
                this.serverAssignor = _readable.readString(length);
            }
        }
        {
            int arrayLength;
            arrayLength = _readable.readUnsignedVarint() - 1;
            if (arrayLength < 0) {
                throw new RuntimeException("non-nullable field assignors was serialized as null");
            } else {
                if (arrayLength > _readable.remaining()) {
                    throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                }
                ArrayList<Assignor> newCollection = new ArrayList<>(arrayLength);
                for (int i = 0; i < arrayLength; i++) {
                    newCollection.add(new Assignor(_readable, _version));
                }
                this.assignors = newCollection;
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
        if (instanceId == null) {
            _writable.writeUnsignedVarint(0);
        } else {
            byte[] _stringBytes = _cache.getSerializedValue(instanceId);
            _writable.writeUnsignedVarint(_stringBytes.length + 1);
            _writable.writeByteArray(_stringBytes);
        }
        if (rackId == null) {
            _writable.writeUnsignedVarint(0);
        } else {
            byte[] _stringBytes = _cache.getSerializedValue(rackId);
            _writable.writeUnsignedVarint(_stringBytes.length + 1);
            _writable.writeByteArray(_stringBytes);
        }
        {
            byte[] _stringBytes = _cache.getSerializedValue(clientId);
            _writable.writeUnsignedVarint(_stringBytes.length + 1);
            _writable.writeByteArray(_stringBytes);
        }
        {
            byte[] _stringBytes = _cache.getSerializedValue(clientHost);
            _writable.writeUnsignedVarint(_stringBytes.length + 1);
            _writable.writeByteArray(_stringBytes);
        }
        _writable.writeUnsignedVarint(subscribedTopicNames.size() + 1);
        for (String subscribedTopicNamesElement : subscribedTopicNames) {
            {
                byte[] _stringBytes = _cache.getSerializedValue(subscribedTopicNamesElement);
                _writable.writeUnsignedVarint(_stringBytes.length + 1);
                _writable.writeByteArray(_stringBytes);
            }
        }
        if (subscribedTopicRegex == null) {
            _writable.writeUnsignedVarint(0);
        } else {
            byte[] _stringBytes = _cache.getSerializedValue(subscribedTopicRegex);
            _writable.writeUnsignedVarint(_stringBytes.length + 1);
            _writable.writeByteArray(_stringBytes);
        }
        _writable.writeInt(rebalanceTimeoutMs);
        if (serverAssignor == null) {
            _writable.writeUnsignedVarint(0);
        } else {
            byte[] _stringBytes = _cache.getSerializedValue(serverAssignor);
            _writable.writeUnsignedVarint(_stringBytes.length + 1);
            _writable.writeByteArray(_stringBytes);
        }
        _writable.writeUnsignedVarint(assignors.size() + 1);
        for (Assignor assignorsElement : assignors) {
            assignorsElement.write(_writable, _cache, _version);
        }
        RawTaggedFieldWriter _rawWriter = RawTaggedFieldWriter.forFields(_unknownTaggedFields);
        _numTaggedFields += _rawWriter.numFields();
        _writable.writeUnsignedVarint(_numTaggedFields);
        _rawWriter.writeRawTags(_writable, Integer.MAX_VALUE);
    }
    
    @Override
    public void addSize(MessageSizeAccumulator _size, ObjectSerializationCache _cache, short _version) {
        int _numTaggedFields = 0;
        if (instanceId == null) {
            _size.addBytes(1);
        } else {
            byte[] _stringBytes = instanceId.getBytes(StandardCharsets.UTF_8);
            if (_stringBytes.length > 0x7fff) {
                throw new RuntimeException("'instanceId' field is too long to be serialized");
            }
            _cache.cacheSerializedValue(instanceId, _stringBytes);
            _size.addBytes(_stringBytes.length + ByteUtils.sizeOfUnsignedVarint(_stringBytes.length + 1));
        }
        if (rackId == null) {
            _size.addBytes(1);
        } else {
            byte[] _stringBytes = rackId.getBytes(StandardCharsets.UTF_8);
            if (_stringBytes.length > 0x7fff) {
                throw new RuntimeException("'rackId' field is too long to be serialized");
            }
            _cache.cacheSerializedValue(rackId, _stringBytes);
            _size.addBytes(_stringBytes.length + ByteUtils.sizeOfUnsignedVarint(_stringBytes.length + 1));
        }
        {
            byte[] _stringBytes = clientId.getBytes(StandardCharsets.UTF_8);
            if (_stringBytes.length > 0x7fff) {
                throw new RuntimeException("'clientId' field is too long to be serialized");
            }
            _cache.cacheSerializedValue(clientId, _stringBytes);
            _size.addBytes(_stringBytes.length + ByteUtils.sizeOfUnsignedVarint(_stringBytes.length + 1));
        }
        {
            byte[] _stringBytes = clientHost.getBytes(StandardCharsets.UTF_8);
            if (_stringBytes.length > 0x7fff) {
                throw new RuntimeException("'clientHost' field is too long to be serialized");
            }
            _cache.cacheSerializedValue(clientHost, _stringBytes);
            _size.addBytes(_stringBytes.length + ByteUtils.sizeOfUnsignedVarint(_stringBytes.length + 1));
        }
        {
            _size.addBytes(ByteUtils.sizeOfUnsignedVarint(subscribedTopicNames.size() + 1));
            for (String subscribedTopicNamesElement : subscribedTopicNames) {
                byte[] _stringBytes = subscribedTopicNamesElement.getBytes(StandardCharsets.UTF_8);
                if (_stringBytes.length > 0x7fff) {
                    throw new RuntimeException("'subscribedTopicNamesElement' field is too long to be serialized");
                }
                _cache.cacheSerializedValue(subscribedTopicNamesElement, _stringBytes);
                _size.addBytes(_stringBytes.length + ByteUtils.sizeOfUnsignedVarint(_stringBytes.length + 1));
            }
        }
        if (subscribedTopicRegex == null) {
            _size.addBytes(1);
        } else {
            byte[] _stringBytes = subscribedTopicRegex.getBytes(StandardCharsets.UTF_8);
            if (_stringBytes.length > 0x7fff) {
                throw new RuntimeException("'subscribedTopicRegex' field is too long to be serialized");
            }
            _cache.cacheSerializedValue(subscribedTopicRegex, _stringBytes);
            _size.addBytes(_stringBytes.length + ByteUtils.sizeOfUnsignedVarint(_stringBytes.length + 1));
        }
        _size.addBytes(4);
        if (serverAssignor == null) {
            _size.addBytes(1);
        } else {
            byte[] _stringBytes = serverAssignor.getBytes(StandardCharsets.UTF_8);
            if (_stringBytes.length > 0x7fff) {
                throw new RuntimeException("'serverAssignor' field is too long to be serialized");
            }
            _cache.cacheSerializedValue(serverAssignor, _stringBytes);
            _size.addBytes(_stringBytes.length + ByteUtils.sizeOfUnsignedVarint(_stringBytes.length + 1));
        }
        {
            _size.addBytes(ByteUtils.sizeOfUnsignedVarint(assignors.size() + 1));
            for (Assignor assignorsElement : assignors) {
                assignorsElement.addSize(_size, _cache, _version);
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
        if (!(obj instanceof ConsumerGroupMemberMetadataValue)) return false;
        ConsumerGroupMemberMetadataValue other = (ConsumerGroupMemberMetadataValue) obj;
        if (this.instanceId == null) {
            if (other.instanceId != null) return false;
        } else {
            if (!this.instanceId.equals(other.instanceId)) return false;
        }
        if (this.rackId == null) {
            if (other.rackId != null) return false;
        } else {
            if (!this.rackId.equals(other.rackId)) return false;
        }
        if (this.clientId == null) {
            if (other.clientId != null) return false;
        } else {
            if (!this.clientId.equals(other.clientId)) return false;
        }
        if (this.clientHost == null) {
            if (other.clientHost != null) return false;
        } else {
            if (!this.clientHost.equals(other.clientHost)) return false;
        }
        if (this.subscribedTopicNames == null) {
            if (other.subscribedTopicNames != null) return false;
        } else {
            if (!this.subscribedTopicNames.equals(other.subscribedTopicNames)) return false;
        }
        if (this.subscribedTopicRegex == null) {
            if (other.subscribedTopicRegex != null) return false;
        } else {
            if (!this.subscribedTopicRegex.equals(other.subscribedTopicRegex)) return false;
        }
        if (rebalanceTimeoutMs != other.rebalanceTimeoutMs) return false;
        if (this.serverAssignor == null) {
            if (other.serverAssignor != null) return false;
        } else {
            if (!this.serverAssignor.equals(other.serverAssignor)) return false;
        }
        if (this.assignors == null) {
            if (other.assignors != null) return false;
        } else {
            if (!this.assignors.equals(other.assignors)) return false;
        }
        return MessageUtil.compareRawTaggedFields(_unknownTaggedFields, other._unknownTaggedFields);
    }
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode = 31 * hashCode + (instanceId == null ? 0 : instanceId.hashCode());
        hashCode = 31 * hashCode + (rackId == null ? 0 : rackId.hashCode());
        hashCode = 31 * hashCode + (clientId == null ? 0 : clientId.hashCode());
        hashCode = 31 * hashCode + (clientHost == null ? 0 : clientHost.hashCode());
        hashCode = 31 * hashCode + (subscribedTopicNames == null ? 0 : subscribedTopicNames.hashCode());
        hashCode = 31 * hashCode + (subscribedTopicRegex == null ? 0 : subscribedTopicRegex.hashCode());
        hashCode = 31 * hashCode + rebalanceTimeoutMs;
        hashCode = 31 * hashCode + (serverAssignor == null ? 0 : serverAssignor.hashCode());
        hashCode = 31 * hashCode + (assignors == null ? 0 : assignors.hashCode());
        return hashCode;
    }
    
    @Override
    public ConsumerGroupMemberMetadataValue duplicate() {
        ConsumerGroupMemberMetadataValue _duplicate = new ConsumerGroupMemberMetadataValue();
        if (instanceId == null) {
            _duplicate.instanceId = null;
        } else {
            _duplicate.instanceId = instanceId;
        }
        if (rackId == null) {
            _duplicate.rackId = null;
        } else {
            _duplicate.rackId = rackId;
        }
        _duplicate.clientId = clientId;
        _duplicate.clientHost = clientHost;
        ArrayList<String> newSubscribedTopicNames = new ArrayList<String>(subscribedTopicNames.size());
        for (String _element : subscribedTopicNames) {
            newSubscribedTopicNames.add(_element);
        }
        _duplicate.subscribedTopicNames = newSubscribedTopicNames;
        if (subscribedTopicRegex == null) {
            _duplicate.subscribedTopicRegex = null;
        } else {
            _duplicate.subscribedTopicRegex = subscribedTopicRegex;
        }
        _duplicate.rebalanceTimeoutMs = rebalanceTimeoutMs;
        if (serverAssignor == null) {
            _duplicate.serverAssignor = null;
        } else {
            _duplicate.serverAssignor = serverAssignor;
        }
        ArrayList<Assignor> newAssignors = new ArrayList<Assignor>(assignors.size());
        for (Assignor _element : assignors) {
            newAssignors.add(_element.duplicate());
        }
        _duplicate.assignors = newAssignors;
        return _duplicate;
    }
    
    @Override
    public String toString() {
        return "ConsumerGroupMemberMetadataValue("
            + "instanceId=" + ((instanceId == null) ? "null" : "'" + instanceId.toString() + "'")
            + ", rackId=" + ((rackId == null) ? "null" : "'" + rackId.toString() + "'")
            + ", clientId=" + ((clientId == null) ? "null" : "'" + clientId.toString() + "'")
            + ", clientHost=" + ((clientHost == null) ? "null" : "'" + clientHost.toString() + "'")
            + ", subscribedTopicNames=" + MessageUtil.deepToString(subscribedTopicNames.iterator())
            + ", subscribedTopicRegex=" + ((subscribedTopicRegex == null) ? "null" : "'" + subscribedTopicRegex.toString() + "'")
            + ", rebalanceTimeoutMs=" + rebalanceTimeoutMs
            + ", serverAssignor=" + ((serverAssignor == null) ? "null" : "'" + serverAssignor.toString() + "'")
            + ", assignors=" + MessageUtil.deepToString(assignors.iterator())
            + ")";
    }
    
    public String instanceId() {
        return this.instanceId;
    }
    
    public String rackId() {
        return this.rackId;
    }
    
    public String clientId() {
        return this.clientId;
    }
    
    public String clientHost() {
        return this.clientHost;
    }
    
    public List<String> subscribedTopicNames() {
        return this.subscribedTopicNames;
    }
    
    public String subscribedTopicRegex() {
        return this.subscribedTopicRegex;
    }
    
    public int rebalanceTimeoutMs() {
        return this.rebalanceTimeoutMs;
    }
    
    public String serverAssignor() {
        return this.serverAssignor;
    }
    
    public List<Assignor> assignors() {
        return this.assignors;
    }
    
    @Override
    public List<RawTaggedField> unknownTaggedFields() {
        if (_unknownTaggedFields == null) {
            _unknownTaggedFields = new ArrayList<>(0);
        }
        return _unknownTaggedFields;
    }
    
    public ConsumerGroupMemberMetadataValue setInstanceId(String v) {
        this.instanceId = v;
        return this;
    }
    
    public ConsumerGroupMemberMetadataValue setRackId(String v) {
        this.rackId = v;
        return this;
    }
    
    public ConsumerGroupMemberMetadataValue setClientId(String v) {
        this.clientId = v;
        return this;
    }
    
    public ConsumerGroupMemberMetadataValue setClientHost(String v) {
        this.clientHost = v;
        return this;
    }
    
    public ConsumerGroupMemberMetadataValue setSubscribedTopicNames(List<String> v) {
        this.subscribedTopicNames = v;
        return this;
    }
    
    public ConsumerGroupMemberMetadataValue setSubscribedTopicRegex(String v) {
        this.subscribedTopicRegex = v;
        return this;
    }
    
    public ConsumerGroupMemberMetadataValue setRebalanceTimeoutMs(int v) {
        this.rebalanceTimeoutMs = v;
        return this;
    }
    
    public ConsumerGroupMemberMetadataValue setServerAssignor(String v) {
        this.serverAssignor = v;
        return this;
    }
    
    public ConsumerGroupMemberMetadataValue setAssignors(List<Assignor> v) {
        this.assignors = v;
        return this;
    }
    
    public static class Assignor implements Message {
        String name;
        short minimumVersion;
        short maximumVersion;
        byte reason;
        short version;
        byte[] metadata;
        private List<RawTaggedField> _unknownTaggedFields;
        
        public static final Schema SCHEMA_0 =
            new Schema(
                new Field("name", Type.COMPACT_STRING, "The assignor name."),
                new Field("minimum_version", Type.INT16, "The minimum version supported by the assignor."),
                new Field("maximum_version", Type.INT16, "The maximum version supported by the assignor."),
                new Field("reason", Type.INT8, "The reason reported by the assignor."),
                new Field("version", Type.INT16, "The version used to serialize the metadata."),
                new Field("metadata", Type.COMPACT_BYTES, "The metadata."),
                TaggedFieldsSection.of(
                )
            );
        
        public static final Schema[] SCHEMAS = new Schema[] {
            SCHEMA_0
        };
        
        public static final short LOWEST_SUPPORTED_VERSION = 0;
        public static final short HIGHEST_SUPPORTED_VERSION = 0;
        
        public Assignor(Readable _readable, short _version) {
            read(_readable, _version);
        }
        
        public Assignor() {
            this.name = "";
            this.minimumVersion = (short) 0;
            this.maximumVersion = (short) 0;
            this.reason = (byte) 0;
            this.version = (short) 0;
            this.metadata = Bytes.EMPTY;
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
                throw new UnsupportedVersionException("Can't read version " + _version + " of Assignor");
            }
            {
                int length;
                length = _readable.readUnsignedVarint() - 1;
                if (length < 0) {
                    throw new RuntimeException("non-nullable field name was serialized as null");
                } else if (length > 0x7fff) {
                    throw new RuntimeException("string field name had invalid length " + length);
                } else {
                    this.name = _readable.readString(length);
                }
            }
            this.minimumVersion = _readable.readShort();
            this.maximumVersion = _readable.readShort();
            this.reason = _readable.readByte();
            this.version = _readable.readShort();
            {
                int length;
                length = _readable.readUnsignedVarint() - 1;
                if (length < 0) {
                    throw new RuntimeException("non-nullable field metadata was serialized as null");
                } else {
                    byte[] newBytes = _readable.readArray(length);
                    this.metadata = newBytes;
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
            {
                byte[] _stringBytes = _cache.getSerializedValue(name);
                _writable.writeUnsignedVarint(_stringBytes.length + 1);
                _writable.writeByteArray(_stringBytes);
            }
            _writable.writeShort(minimumVersion);
            _writable.writeShort(maximumVersion);
            _writable.writeByte(reason);
            _writable.writeShort(version);
            _writable.writeUnsignedVarint(metadata.length + 1);
            _writable.writeByteArray(metadata);
            RawTaggedFieldWriter _rawWriter = RawTaggedFieldWriter.forFields(_unknownTaggedFields);
            _numTaggedFields += _rawWriter.numFields();
            _writable.writeUnsignedVarint(_numTaggedFields);
            _rawWriter.writeRawTags(_writable, Integer.MAX_VALUE);
        }
        
        @Override
        public void addSize(MessageSizeAccumulator _size, ObjectSerializationCache _cache, short _version) {
            int _numTaggedFields = 0;
            if (_version > 0) {
                throw new UnsupportedVersionException("Can't size version " + _version + " of Assignor");
            }
            {
                byte[] _stringBytes = name.getBytes(StandardCharsets.UTF_8);
                if (_stringBytes.length > 0x7fff) {
                    throw new RuntimeException("'name' field is too long to be serialized");
                }
                _cache.cacheSerializedValue(name, _stringBytes);
                _size.addBytes(_stringBytes.length + ByteUtils.sizeOfUnsignedVarint(_stringBytes.length + 1));
            }
            _size.addBytes(2);
            _size.addBytes(2);
            _size.addBytes(1);
            _size.addBytes(2);
            {
                _size.addBytes(metadata.length);
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(metadata.length + 1));
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
            if (!(obj instanceof Assignor)) return false;
            Assignor other = (Assignor) obj;
            if (this.name == null) {
                if (other.name != null) return false;
            } else {
                if (!this.name.equals(other.name)) return false;
            }
            if (minimumVersion != other.minimumVersion) return false;
            if (maximumVersion != other.maximumVersion) return false;
            if (reason != other.reason) return false;
            if (version != other.version) return false;
            if (!Arrays.equals(this.metadata, other.metadata)) return false;
            return MessageUtil.compareRawTaggedFields(_unknownTaggedFields, other._unknownTaggedFields);
        }
        
        @Override
        public int hashCode() {
            int hashCode = 0;
            hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
            hashCode = 31 * hashCode + minimumVersion;
            hashCode = 31 * hashCode + maximumVersion;
            hashCode = 31 * hashCode + reason;
            hashCode = 31 * hashCode + version;
            hashCode = 31 * hashCode + Arrays.hashCode(metadata);
            return hashCode;
        }
        
        @Override
        public Assignor duplicate() {
            Assignor _duplicate = new Assignor();
            _duplicate.name = name;
            _duplicate.minimumVersion = minimumVersion;
            _duplicate.maximumVersion = maximumVersion;
            _duplicate.reason = reason;
            _duplicate.version = version;
            _duplicate.metadata = MessageUtil.duplicate(metadata);
            return _duplicate;
        }
        
        @Override
        public String toString() {
            return "Assignor("
                + "name=" + ((name == null) ? "null" : "'" + name.toString() + "'")
                + ", minimumVersion=" + minimumVersion
                + ", maximumVersion=" + maximumVersion
                + ", reason=" + reason
                + ", version=" + version
                + ", metadata=" + Arrays.toString(metadata)
                + ")";
        }
        
        public String name() {
            return this.name;
        }
        
        public short minimumVersion() {
            return this.minimumVersion;
        }
        
        public short maximumVersion() {
            return this.maximumVersion;
        }
        
        public byte reason() {
            return this.reason;
        }
        
        public short version() {
            return this.version;
        }
        
        public byte[] metadata() {
            return this.metadata;
        }
        
        @Override
        public List<RawTaggedField> unknownTaggedFields() {
            if (_unknownTaggedFields == null) {
                _unknownTaggedFields = new ArrayList<>(0);
            }
            return _unknownTaggedFields;
        }
        
        public Assignor setName(String v) {
            this.name = v;
            return this;
        }
        
        public Assignor setMinimumVersion(short v) {
            this.minimumVersion = v;
            return this;
        }
        
        public Assignor setMaximumVersion(short v) {
            this.maximumVersion = v;
            return this;
        }
        
        public Assignor setReason(byte v) {
            this.reason = v;
            return this;
        }
        
        public Assignor setVersion(short v) {
            this.version = v;
            return this;
        }
        
        public Assignor setMetadata(byte[] v) {
            this.metadata = v;
            return this;
        }
    }
}
