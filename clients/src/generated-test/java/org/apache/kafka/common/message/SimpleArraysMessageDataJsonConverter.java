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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ShortNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.util.ArrayList;
import org.apache.kafka.common.errors.UnsupportedVersionException;
import org.apache.kafka.common.protocol.MessageUtil;

import static org.apache.kafka.common.message.SimpleArraysMessageData.*;

public class SimpleArraysMessageDataJsonConverter {
    public static SimpleArraysMessageData read(JsonNode _node, short _version) {
        SimpleArraysMessageData _object = new SimpleArraysMessageData();
        JsonNode _goatsNode = _node.get("goats");
        if (_goatsNode == null) {
            if (_version >= 1) {
                throw new RuntimeException("SimpleArraysMessageData: unable to locate field 'goats', which is mandatory in version " + _version);
            } else {
                _object.goats = new ArrayList<StructArray>(0);
            }
        } else {
            if (!_goatsNode.isArray()) {
                throw new RuntimeException("SimpleArraysMessageData expected a JSON array, but got " + _node.getNodeType());
            }
            ArrayList<StructArray> _collection = new ArrayList<StructArray>(_goatsNode.size());
            _object.goats = _collection;
            for (JsonNode _element : _goatsNode) {
                _collection.add(StructArrayJsonConverter.read(_element, _version));
            }
        }
        JsonNode _sheepNode = _node.get("sheep");
        if (_sheepNode == null) {
            throw new RuntimeException("SimpleArraysMessageData: unable to locate field 'sheep', which is mandatory in version " + _version);
        } else {
            if (!_sheepNode.isArray()) {
                throw new RuntimeException("SimpleArraysMessageData expected a JSON array, but got " + _node.getNodeType());
            }
            ArrayList<Integer> _collection = new ArrayList<Integer>(_sheepNode.size());
            _object.sheep = _collection;
            for (JsonNode _element : _sheepNode) {
                _collection.add(MessageUtil.jsonNodeToInt(_element, "SimpleArraysMessageData element"));
            }
        }
        return _object;
    }
    public static JsonNode write(SimpleArraysMessageData _object, short _version, boolean _serializeRecords) {
        ObjectNode _node = new ObjectNode(JsonNodeFactory.instance);
        if (_version >= 1) {
            ArrayNode _goatsArray = new ArrayNode(JsonNodeFactory.instance);
            for (StructArray _element : _object.goats) {
                _goatsArray.add(StructArrayJsonConverter.write(_element, _version, _serializeRecords));
            }
            _node.set("goats", _goatsArray);
        } else {
            if (!_object.goats.isEmpty()) {
                throw new UnsupportedVersionException("Attempted to write a non-default goats at version " + _version);
            }
        }
        ArrayNode _sheepArray = new ArrayNode(JsonNodeFactory.instance);
        for (Integer _element : _object.sheep) {
            _sheepArray.add(new IntNode(_element));
        }
        _node.set("sheep", _sheepArray);
        return _node;
    }
    public static JsonNode write(SimpleArraysMessageData _object, short _version) {
        return write(_object, _version, true);
    }
    
    public static class StructArrayJsonConverter {
        public static StructArray read(JsonNode _node, short _version) {
            StructArray _object = new StructArray();
            if (_version < 1) {
                throw new UnsupportedVersionException("Can't read version " + _version + " of StructArray");
            }
            JsonNode _colorNode = _node.get("color");
            if (_colorNode == null) {
                throw new RuntimeException("StructArray: unable to locate field 'color', which is mandatory in version " + _version);
            } else {
                _object.color = MessageUtil.jsonNodeToByte(_colorNode, "StructArray");
            }
            JsonNode _nameNode = _node.get("name");
            if (_nameNode == null) {
                if (_version >= 2) {
                    throw new RuntimeException("StructArray: unable to locate field 'name', which is mandatory in version " + _version);
                } else {
                    _object.name = "";
                }
            } else {
                if (!_nameNode.isTextual()) {
                    throw new RuntimeException("StructArray expected a string type, but got " + _node.getNodeType());
                }
                _object.name = _nameNode.asText();
            }
            return _object;
        }
        public static JsonNode write(StructArray _object, short _version, boolean _serializeRecords) {
            if (_version < 1) {
                throw new UnsupportedVersionException("Can't write version " + _version + " of StructArray");
            }
            ObjectNode _node = new ObjectNode(JsonNodeFactory.instance);
            _node.set("color", new ShortNode(_object.color));
            if (_version >= 2) {
                _node.set("name", new TextNode(_object.name));
            } else {
                if (!_object.name.equals("")) {
                    throw new UnsupportedVersionException("Attempted to write a non-default name at version " + _version);
                }
            }
            return _node;
        }
        public static JsonNode write(StructArray _object, short _version) {
            return write(_object, _version, true);
        }
    }
}
