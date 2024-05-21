/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.apache.dubbo.spi.samples.configcenter;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.launcher.EtcdCluster;
import io.etcd.jetcd.launcher.EtcdClusterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.nio.charset.StandardCharsets.UTF_8;

public class EtcdUtil {
    private static final Logger logger = LoggerFactory.getLogger(EtcdUtil.class);

    public static EtcdCluster etcdCluster;

    public static Client client;

    public static void start() {
        try {
            etcdCluster = EtcdClusterFactory.buildCluster(EtcdUtil.class.getSimpleName(), 1, false);

            etcdCluster.start();
            client = Client.builder().endpoints(etcdCluster.getClientEndpoints()).build();

            List<URI> clientEndPoints = etcdCluster.getClientEndpoints();

            String ipAddress = clientEndPoints.get(0).getHost() + ":" + clientEndPoints.get(0).getPort(); //"127.0.0.1:2379";

            logger.info("etcdStarted.... ipAddress = " + ipAddress);
        } catch (Exception e) {
            logger.error("Failed to start etcd cluster", e);
        }
    }

    public static void close(){
        etcdCluster.close();
        client.close();
    }

    private static void put(String key, String value) {
        try {
            client.getKVClient().put(ByteSequence.from(key, UTF_8), ByteSequence.from(value, UTF_8)).get();
        } catch (Exception e) {
            System.out.println("Error put value to etcd.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        start();
        put("/dubbo/config/test/dubbo.properties", "aaa=bbb");
        new CountDownLatch(1).await();
//        close();
    }
}
