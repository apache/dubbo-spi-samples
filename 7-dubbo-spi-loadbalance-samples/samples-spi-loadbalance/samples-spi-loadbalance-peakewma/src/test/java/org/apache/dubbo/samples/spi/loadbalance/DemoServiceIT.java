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

package org.apache.dubbo.samples.spi.loadbalance;

import org.apache.dubbo.samples.spi.loadbalance.api.DemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/configcenter-consumer.xml")
public class DemoServiceIT {
    @Autowired
    @Qualifier("demoService")
    private DemoService demoService;

    @Test
    public void testCombined() throws Exception {
        // Part 1: Run first part of the test
        Map<String, Integer> providerCount1 = new HashMap<>();

        for (int i = 0; i < 1000; i++) {
            String response = demoService.sayHello("world");
            String[] parts = response.split(":");
            String provider = parts[parts.length - 1].trim();

            // Count the number of calls for each provider
            providerCount1.put(provider, providerCount1.getOrDefault(provider, 0) + 1);
        }

        // Calculate the difference for the first part
        int provider1Calls1 = providerCount1.getOrDefault("20880", 0);
        int provider2Calls1 = providerCount1.getOrDefault("20881", 0);
        int diff1 = Math.abs(provider1Calls1 - provider2Calls1);

        System.out.println("Part 1 - Difference: " + diff1);

        // Part 2: Run second part of the test
        Map<String, Integer> providerCount2 = new HashMap<>();
        int time = 0;
        for (int i = 0; i < 1000; i++) {
            String response;
            // Make provider1 slow every 10 requests
            if (i % 10 == 0) {
                response = slowProviderCall(time);
            } else {
                response = demoService.sayHello("world");
            }
            String[] parts = response.split(":");
            String provider = parts[parts.length - 1].trim();

            // Count the number of calls for each provider
            providerCount2.put(provider, providerCount2.getOrDefault(provider, 0) + 1);
        }

        // Calculate the difference for the second part
        int provider1Calls2 = providerCount2.getOrDefault("20880", 0);
        int provider2Calls2 = providerCount2.getOrDefault("20881", 0);
        int diff2 = Math.abs(provider1Calls2 - provider2Calls2);

        System.out.println("Part 2 - Difference: " + diff2);

        // Assert that the difference from the first part is less than the difference from the second part
        Assert.assertTrue("The difference from the first part should be less than the difference from the second part", diff1 < diff2);
    }

    // Simulate a slow call to provider1
    private String slowProviderCall(int time) {
        String ipAddress = "unknown";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ipAddress = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            // Simulate a time-consuming operation, such as a remote service call or database query
            Thread.sleep(500 + time);
            time += 30;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Hello world, response from provider: " + ipAddress + ":20880";
    }
}
