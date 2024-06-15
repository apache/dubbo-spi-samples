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

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/configcenter-consumer.xml")
public class DemoServiceIT {
    @Autowired
    @Qualifier("demoService")
    private DemoService demoService;

    @Test
    public void test1() throws Exception {
        Map<String, Integer> providerCount = new HashMap<>();

        for (int i = 0; i < 1000; i++) {
            String response = demoService.sayHello("world");
            String[] parts = response.split(":");
            String provider = parts[parts.length - 1].trim();

            // Count the number of calls for each provider
            providerCount.put(provider, providerCount.getOrDefault(provider, 0) + 1);
        }

        // Print the number of calls for each provider and calculate the difference
        int provider1Calls = providerCount.getOrDefault("20880", 0);
        int provider2Calls = providerCount.getOrDefault("20881", 0);

        for (Map.Entry<String, Integer> entry : providerCount.entrySet()) {
            System.out.println("Provider " + entry.getKey() + " calls: " + entry.getValue());
        }

        // Assert that the absolute difference between the two provider call counts is less than 50
        int diff = Math.abs(provider1Calls - provider2Calls);
        System.out.println("Difference: " + diff);
        Assert.assertTrue("The absolute difference between provider call counts should be less than 60", diff <= 60);
    }

    @Test
    public void test2() throws Exception {
        Map<String, Integer> providerCount = new HashMap<>();
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
            providerCount.put(provider, providerCount.getOrDefault(provider, 0) + 1);
        }

        // Print the number of calls for each provider and calculate the difference
        int provider1Calls = providerCount.getOrDefault("20880", 0);
        int provider2Calls = providerCount.getOrDefault("20881", 0);

        for (Map.Entry<String, Integer> entry : providerCount.entrySet()) {
            System.out.println("Provider " + entry.getKey() + " calls: " + entry.getValue());
        }

        // Assert that the difference between the two provider call counts is greater than 80
        int diff = Math.abs(provider1Calls - provider2Calls);
        System.out.println("Difference: " + diff);
        Assert.assertTrue("The difference between provider call counts should be greater than 60", diff > 60);
    }

    // Simulate a slow call to provider1
    private String slowProviderCall(int time) {
        try {
            // Simulate a time-consuming operation, such as a remote service call or database query
            Thread.sleep(500 + time);
            time += 30;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Hello world, response from provider1";
    }
}
