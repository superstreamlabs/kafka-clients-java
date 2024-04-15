/*
 * Copyright 2022 [Your Name] or [Your Company]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.superstream;

public class Consts {
    public static final String sdkVersion  = "1.0.0";
    public static final String clientReconnectionUpdateSubject  = "internal.clientReconnectionUpdate";
    public static final String clientTypeUpdateSubject  = "internal.clientTypeUpdate";
    public static final String clientRegisterSubject  = "internal.registerClient";
    public static final String originalSerializer = "original.serializer";
    public static final String originalDeserializer = "original.deserializer";
    public static final String superstreamDefaultHost = "broker.superstream.dev";
    public static final String superstreamErrorSubject = "internal.clientErrors";
    public static final String superstreamUpdatesSubject  = "internal.updates.%d";
    public static final String superstreamClientsUpdateSubject  = "internal_tasks.clientsUpdate.%s.%d";
    public static final String superstreamLearningSubject  = "internal.schema.learnSchema.%d";
    public static final String superstreamRegisterSchemaSubject  = "internal_tasks.schema.registerSchema.%d";
    public static final String superstreamInternalUsername = "superstream_internal";
    public static final String superstreamGetSchemaSubject = "internal.schema.getSchema.%d";
    public static final Integer superstreamDefaultLearningFactor = 20;
    public static final String superstreamLearningFactorKey = "superstream.learning.factor";
    public static final String superstreamHostKey = "superstream.host";
    public static final String superstreamTokenKey = "superstream.token";
}
