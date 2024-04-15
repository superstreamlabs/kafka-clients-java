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

import io.nats.client.AuthHandler;
import io.nats.client.NKey;

public class NatsAuthHandler implements AuthHandler {
    private final String jwt;
    private final NKey nkey;

    public NatsAuthHandler(String jwt, String nkeySeed) {
        this.jwt = jwt;
        this.nkey = NKey.fromSeed(nkeySeed.toCharArray());
    }

    @Override
    public char[] getID() {
        return jwt.toCharArray();
    }

    @Override
    public byte[] sign(byte[] nonce) {
        try {
            return nkey.sign(nonce);
        } catch (Exception e) {
            // Handle signing error
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public char[] getJWT() {
        return jwt.toCharArray();
    }
}