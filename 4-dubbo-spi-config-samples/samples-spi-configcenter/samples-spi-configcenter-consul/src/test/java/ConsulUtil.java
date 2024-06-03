/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.dubbo.common.URL;
import org.apache.dubbo.configcenter.consul.ConsulDynamicConfiguration;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import com.pszymczyk.consul.ConsulProcess;
import com.pszymczyk.consul.ConsulStarterBuilder;

import java.util.concurrent.CountDownLatch;


public class ConsulUtil {

    private static ConsulProcess consul;
    private static URL configCenterUrl;
    private static ConsulDynamicConfiguration configuration;

    private static Consul client;
    private static KeyValueClient kvClient;

    public static void setUp() throws Exception {
        consul = ConsulStarterBuilder.consulStarter()
                .withHttpPort(8500)
                .build()
                .start();
        configCenterUrl = URL.valueOf("consul://127.0.0.1:" + consul.getHttpPort());

        configuration = new ConsulDynamicConfiguration(configCenterUrl);
        client = Consul.builder().withHostAndPort(HostAndPort.fromParts("127.0.0.1", consul.getHttpPort())).build();
        kvClient = client.keyValueClient();
    }

    public static void main(String[] args) throws Exception {
        setUp();
        new CountDownLatch(1).await();
    }

    public static void tearDown() throws Exception {
        consul.close();
        configuration.close();
    }


}
