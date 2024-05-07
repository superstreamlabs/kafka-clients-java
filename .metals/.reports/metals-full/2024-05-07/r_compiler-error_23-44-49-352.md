file://<WORKSPACE>/clients/src/test/java/org/apache/kafka/common/security/TestSecurityConfig.java
### java.util.NoSuchElementException: next on empty iterator

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.3
Classpath:
<HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ]
Options:



action parameters:
uri: file://<WORKSPACE>/clients/src/test/java/org/apache/kafka/common/security/TestSecurityConfig.java
text:
```scala
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
package org.apache.kafka.common.security;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.internals.BrokerSecurityConfigs;

import java.util.Map;

public class TestSecurityConfig extends AbstractConfig {
    private static final ConfigDef CONFIG = new ConfigDef()
            .define(BrokerSecurityConfigs.SSL_CLIENT_AUTH_CONFIG, Type.STRING, null, Importance.MEDIUM,
                    BrokerSecurityConfigs.SSL_CLIENT_AUTH_DOC)
            .define(BrokerSecurityConfigs.SASL_ENABLED_MECHANISMS_CONFIG, Type.LIST,
                    BrokerSecurityConfigs.DEFAULT_SASL_ENABLED_MECHANISMS,
                    Importance.MEDIUM, BrokerSecurityConfigs.SASL_ENABLED_MECHANISMS_DOC)
            .define(BrokerSecurityConfigs.SASL_SERVER_CALLBACK_HANDLER_CLASS, Type.CLASS,
                    null,
                    Importance.MEDIUM, BrokerSecurityConfigs.SASL_SERVER_CALLBACK_HANDLER_CLASS_DOC)
            .define(BrokerSecurityConfigs.PRINCIPAL_BUILDER_CLASS_CONFIG, Type.CLASS,
                    null, Importance.MEDIUM, BrokerSecurityConfigs.PRINCIPAL_BUILDER_CLASS_DOC)
            .define(BrokerSecurityConfigs.CONNECTIONS_MAX_REAUTH_MS, Type.LONG, 0L, Importance.MEDIUM,
                    BrokerSecurityConfigs.CONNECTIONS_MAX_REAUTH_MS_DOC)
            .define(BrokerSecurityConfigs.SASL_SERVER_MAX_RECEIVE_SIZE_CONFIG, Type.INT, BrokerSecurityConfigs.DEFAULT_SASL_SERVER_MAX_RECEIVE_SIZE,
                    Importance.LOW, BrokerSecurityConfigs.SASL_SERVER_MAX_RECEIVE_SIZE_DOC)
            .withClientSslSupport()
            .withClientSaslSupport();

    public TestSecurityConfig(Map<?, ?> originals) {
        super(CONFIG, originals, false, "");
    }
}

```



#### Error stacktrace:

```
scala.collection.Iterator$$anon$19.next(Iterator.scala:973)
	scala.collection.Iterator$$anon$19.next(Iterator.scala:971)
	scala.collection.mutable.MutationTracker$CheckedIterator.next(MutationTracker.scala:76)
	scala.collection.IterableOps.head(Iterable.scala:222)
	scala.collection.IterableOps.head$(Iterable.scala:222)
	scala.collection.AbstractIterable.head(Iterable.scala:933)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:168)
	scala.meta.internal.pc.MetalsDriver.run(MetalsDriver.scala:45)
	scala.meta.internal.pc.PcCollector.<init>(PcCollector.scala:44)
	scala.meta.internal.pc.PcSemanticTokensProvider$Collector$.<init>(PcSemanticTokensProvider.scala:61)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector$lzyINIT1(PcSemanticTokensProvider.scala:61)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector(PcSemanticTokensProvider.scala:61)
	scala.meta.internal.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:90)
	scala.meta.internal.pc.ScalaPresentationCompiler.semanticTokens$$anonfun$1(ScalaPresentationCompiler.scala:110)
```
#### Short summary: 

java.util.NoSuchElementException: next on empty iterator