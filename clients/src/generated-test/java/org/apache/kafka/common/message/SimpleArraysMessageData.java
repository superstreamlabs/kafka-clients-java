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

package org.apache.kafka.common.message;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.common.errors.UnsupportedVersionException;
import org.apache.kafka.common.protocol.ApiMessage;
import org.apache.kafka.common.protocol.Message;
import org.apache.kafka.common.protocol.MessageSizeAccumulator;
import org.apache.kafka.common.protocol.MessageUtil;
import org.apache.kafka.common.protocol.ObjectSerializationCache;
import org.apache.kafka.common.protocol.Readable;
import org.apache.kafka.common.protocol.Writable;
import org.apache.kafka.common.protocol.types.ArrayOf;
import org.apache.kafka.common.protocol.types.CompactArrayOf;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.protocol.types.RawTaggedField;
import org.apache.kafka.common.protocol.types.RawTaggedFieldWriter;
import org.apache.kafka.common.protocol.types.Schema;
import org.apache.kafka.common.protocol.types.Type;
import org.apache.kafka.common.utils.ByteUtils;

import static org.apache.kafka.common.protocol.types.Field.TaggedFieldsSection;


public class SimpleArraysMessageData implements ApiMessage {
    List<StructArray> goats;
    List<Integer> sheep;
    private List<RawTaggedField> _unknownTaggedFields;
    
    public static final Schema SCHEMA_0 =
        new Schema(
            new Field("sheep", new ArrayOf(Type.INT32), "")
        );
    
    public static final Schema SCHEMA_1 =
        new Schema(
            new Field("goats", new CompactArrayOf(StructArray.SCHEMA_1), ""),
            new Field("sheep", new CompactArrayOf(Type.INT32), ""),
            TaggedFieldsSection.of(
            )
        );
    
    public static final Schema SCHEMA_2 =
        new Schema(
            new Field("goats", new CompactArrayOf(StructArray.SCHEMA_2), ""),
            new Field("sheep", new CompactArrayOf(Type.INT32), ""),
            TaggedFieldsSection.of(
            )
        );
    
    public static final Schema[] SCHEMAS = new Schema[] {
        SCHEMA_0,
        SCHEMA_1,
        SCHEMA_2
    };
    
    public static final short LOWEST_SUPPORTED_VERSION = 0;
    public static final short HIGHEST_SUPPORTED_VERSION = 2;
    
    public SimpleArraysMessageData(Readable _readable, short _version) {
        read(_readable, _version);
    }
    
    public SimpleArraysMessageData() {
        this.goats = new ArrayList<StructArray>(0);
        this.sheep = new ArrayList<Integer>(0);
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
        return 2;
    }
    
    @Override
    public void read(Readable _readable, short _version) {
        if (_version >= 1) {
            int arrayLength;
            arrayLength = _readable.readUnsignedVarint() - 1;
            if (arrayLength < 0) {
                throw new RuntimeException("non-nullable field goats was serialized as null");
            } else {
                if (arrayLength > _readable.remaining()) {
                    throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                }
                ArrayList<StructArray> newCollection = new ArrayList<>(arrayLength);
                for (int i = 0; i < arrayLength; i++) {
                    newCollection.add(new StructArray(_readable, _version));
                }
                this.goats = newCollection;
            }
        } else {
            this.goats = new ArrayList<StructArray>(0);
        }
        {
            int arrayLength;
            if (_version >= 1) {
                arrayLength = _readable.readUnsignedVarint() - 1;
            } else {
                arrayLength = _readable.readInt();
            }
            if (arrayLength < 0) {
                throw new RuntimeException("non-nullable field sheep was serialized as null");
            } else {
                if (arrayLength > _readable.remaining()) {
                    throw new RuntimeException("Tried to allocate a collection of size " + arrayLength + ", but there are only " + _readable.remaining() + " bytes remaining.");
                }
                ArrayList<Integer> newCollection = new ArrayList<>(arrayLength);
                for (int i = 0; i < arrayLength; i++) {
                    newCollection.add(_readable.readInt());
                }
                this.sheep = newCollection;
            }
        }
        this._unknownTaggedFields = null;
        if (_version >= 1) {
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
    }
    
    @Override
    public void write(Writable _writable, ObjectSerializationCache _cache, short _version) {
        int _numTaggedFields = 0;
        if (_version >= 1) {
            _writable.writeUnsignedVarint(goats.size() + 1);
            for (StructArray goatsElement : goats) {
                goatsElement.write(_writable, _cache, _version);
            }
        } else {
            if (!this.goats.isEmpty()) {
                throw new UnsupportedVersionException("Attempted to write a non-default goats at version " + _version);
            }
        }
        if (_version >= 1) {
            _writable.writeUnsignedVarint(sheep.size() + 1);
        } else {
            _writable.writeInt(sheep.size());
        }
        for (Integer sheepElement : sheep) {
            _writable.writeInt(sheepElement);
        }
        RawTaggedFieldWriter _rawWriter = RawTaggedFieldWriter.forFields(_unknownTaggedFields);
        _numTaggedFields += _rawWriter.numFields();
        if (_version >= 1) {
            _writable.writeUnsignedVarint(_numTaggedFields);
            _rawWriter.writeRawTags(_writable, Integer.MAX_VALUE);
        } else {
            if (_numTaggedFields > 0) {
                throw new UnsupportedVersionException("Tagged fields were set, but version " + _version + " of this message does not support them.");
            }
        }
    }
    
    @Override
    public void addSize(MessageSizeAccumulator _size, ObjectSerializationCache _cache, short _version) {
        int _numTaggedFields = 0;
        if (_version >= 1) {
            {
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(goats.size() + 1));
                for (StructArray goatsElement : goats) {
                    goatsElement.addSize(_size, _cache, _version);
                }
            }
        }
        {
            if (_version >= 1) {
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(sheep.size() + 1));
            } else {
                _size.addBytes(4);
            }
            _size.addBytes(sheep.size() * 4);
        }
        if (_unknownTaggedFields != null) {
            _numTaggedFields += _unknownTaggedFields.size();
            for (RawTaggedField _field : _unknownTaggedFields) {
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_field.tag()));
                _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_field.size()));
                _size.addBytes(_field.size());
            }
        }
        if (_version >= 1) {
            _size.addBytes(ByteUtils.sizeOfUnsignedVarint(_numTaggedFields));
        } else {
            if (_numTaggedFields > 0) {
                throw new UnsupportedVersionException("Tagged fields were set, but version " + _version + " of this message does not support them.");
            }
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SimpleArraysMessageData)) return false;
        SimpleArraysMessageData other = (SimpleArraysMessageData) obj;
        if (this.goats == null) {
            if (other.goats != null) return false;
        } else {
            if (!this.goats.equals(other.goats)) return false;
        }
        if (this.sheep == null) {
            if (other.sheep != null) return false;
        } else {
            if (!this.sheep.equals(other.sheep)) return false;
        }
        return MessageUtil.compareRawTaggedFields(_unknownTaggedFields, other._unknownTaggedFields);
    }
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode = 31 * hashCode + (goats == null ? 0 : goats.hashCode());
        hashCode = 31 * hashCode + (sheep == null ? 0 : sheep.hashCode());
        return hashCode;
    }
    
    @Override
    public SimpleArraysMessageData duplicate() {
        SimpleArraysMessageData _duplicate = new SimpleArraysMessageData();
        ArrayList<StructArray> newGoats = new ArrayList<StructArray>(goats.size());
        for (StructArray _element : goats) {
            newGoats.add(_element.duplicate());
        }
        _duplicate.goats = newGoats;
        ArrayList<Integer> newSheep = new ArrayList<Integer>(sheep.size());
        for (Integer _element : sheep) {
            newSheep.add(_element);
        }
        _duplicate.sheep = newSheep;
        return _duplicate;
    }
    
    @Override
    public String toString() {
        return "SimpleArraysMessageData("
            + "goats=" + MessageUtil.deepToString(goats.iterator())
            + ", sheep=" + MessageUtil.deepToString(sheep.iterator())
            + ")";
    }
    
    public List<StructArray> goats() {
        return this.goats;
    }
    
    public List<Integer> sheep() {
        return this.sheep;
    }
    
    @Override
    public List<RawTaggedField> unknownTaggedFields() {
        if (_unknownTaggedFields == null) {
            _unknownTaggedFields = new ArrayList<>(0);
        }
        return _unknownTaggedFields;
    }
    
    public SimpleArraysMessageData setGoats(List<StructArray> v) {
        this.goats = v;
        return this;
    }
    
    public SimpleArraysMessageData setSheep(List<Integer> v) {
        this.sheep = v;
        return this;
    }
    
    public static class StructArray implements Message {
        byte color;
        String name;
        private List<RawTaggedField> _unknownTaggedFields;
        
        public static final Schema SCHEMA_1 =
            new Schema(
                new Field("color", Type.INT8, ""),
                TaggedFieldsSection.of(
                )
            );
        
        public static final Schema SCHEMA_2 =
            new Schema(
                new Field("color", Type.INT8, ""),
                new Field("name", Type.COMPACT_STRING, ""),
                TaggedFieldsSection.of(
                )
            );
        
        public static final Schema[] SCHEMAS = new Schema[] {
            null,
            SCHEMA_1,
            SCHEMA_2
        };
        
        public static final short LOWEST_SUPPORTED_VERSION = 1;
        public static final short HIGHEST_SUPPORTED_VERSION = 2;
        
        public StructArray(Readable _readable, short _version) {
            read(_readable, _version);
        }
        
        public StructArray() {
            this.color = (byte) 0;
            this.name = "";
        }
        
        
        @Override
        public short lowestSupportedVersion() {
            return 0;
        }
        
        @Override
        public short highestSupportedVersion() {
            return 2;
        }
        
        @Override
        public void read(Readable _readable, short _version) {
            if (_version > 2) {
                throw new UnsupportedVersionException("Can't read version " + _version + " of StructArray");
            }
            this.color = _readable.readByte();
            if (_version >= 2) {
                int length;
                length = _readable.readUnsignedVarint() - 1;
                if (length < 0) {
                    throw new RuntimeException("non-nullable field name was serialized as null");
                } else if (length > 0x7fff) {
                    throw new RuntimeException("string field name had invalid length " + length);
                } else {
                    this.name = _readable.readString(length);
                }
            } else {
                this.name = "";
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
            if (_version < 1) {
                throw new UnsupportedVersionException("Can't write version " + _version + " of StructArray");
            }
            int _numTaggedFields = 0;
            _writable.writeByte(color);
            if (_version >= 2) {
                {
                    byte[] _stringBytes = _cache.getSerializedValue(name);
                    _writable.writeUnsignedVarint(_stringBytes.length + 1);
                    _writable.writeByteArray(_stringBytes);
                }
            } else {
                if (!this.name.equals("")) {
                    throw new UnsupportedVersionException("Attempted to write a non-default name at version " + _version);
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
            if (_version > 2) {
                throw new UnsupportedVersionException("Can't size version " + _version + " of StructArray");
            }
            _size.addBytes(1);
            if (_version >= 2) {
                {
                    byte[] _stringBytes = name.getBytes(StandardCharsets.UTF_8);
                    if (_stringBytes.length > 0x7fff) {
                        throw new RuntimeException("'name' field is too long to be serialized");
                    }
                    _cache.cacheSerializedValue(name, _stringBytes);
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
            if (!(obj instanceof StructArray)) return false;
            StructArray other = (StructArray) obj;
            if (color != other.color) return false;
            if (this.name == null) {
                if (other.name != null) return false;
            } else {
                if (!this.name.equals(other.name)) return false;
            }
            return MessageUtil.compareRawTaggedFields(_unknownTaggedFields, other._unknownTaggedFields);
        }
        
        @Override
        public int hashCode() {
            int hashCode = 0;
            hashCode = 31 * hashCode + color;
            hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
            return hashCode;
        }
        
        @Override
        public StructArray duplicate() {
            StructArray _duplicate = new StructArray();
            _duplicate.color = color;
            _duplicate.name = name;
            return _duplicate;
        }
        
        @Override
        public String toString() {
            return "StructArray("
                + "color=" + color
                + ", name=" + ((name == null) ? "null" : "'" + name.toString() + "'")
                + ")";
        }
        
        public byte color() {
            return this.color;
        }
        
        public String name() {
            return this.name;
        }
        
        @Override
        public List<RawTaggedField> unknownTaggedFields() {
            if (_unknownTaggedFields == null) {
                _unknownTaggedFields = new ArrayList<>(0);
            }
            return _unknownTaggedFields;
        }
        
        public StructArray setColor(byte v) {
            this.color = v;
            return this;
        }
        
        public StructArray setName(String v) {
            this.name = v;
            return this;
        }
    }
}
