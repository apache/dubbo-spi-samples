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

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.samples.spi.loadbalance.api.DemoService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@EnableDubbo
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/configcenter-consumer.xml")
public class DemoServiceIT {

    @DubboReference
    private DemoService demoService;

    @Test
    public void testCombined() throws Exception {
        // Warm-up phase
        warmUp(100);

        // Part 1: Normal requests
        Map<String, Integer> providerCount1 = new HashMap<>();
        int totalRequests = 1000;

        for (int i = 0; i < totalRequests; i++) {
            String response = demoService.sayHello("world");
            String provider = extractProvider(response);
            providerCount1.put(provider, providerCount1.getOrDefault(provider, 0) + 1);
        }

        // Calculate the ratio for the first part
        int provider1Calls1 = providerCount1.getOrDefault("20880", 0);
        int provider2Calls1 = providerCount1.getOrDefault("20881", 0);
        double ratio1 = (double) provider1Calls1 / provider2Calls1;

        System.out.println("Part 1 - Provider1 Calls: " + provider1Calls1 + ", Provider2 Calls: " + provider2Calls1 + ", Ratio: " + ratio1);

        // Part 2: Mixed requests with 10% slow calls
        Map<String, Integer> providerCount2 = new HashMap<>();
        int slowCallInterval = 10; // Every 10 requests, make a slow call
        int slowCallDelay = 1000; // Slow call delay of 1 second

        for (int i = 0; i < totalRequests; i++) {
            String response;
            if (i % slowCallInterval == 0) {
                response = slowProviderCall(slowCallDelay);
            } else {
                response = demoService.sayHello("world");
            }
            String provider = extractProvider(response);
            providerCount2.put(provider, providerCount2.getOrDefault(provider, 0) + 1);
        }

        // Calculate the ratio for the second part
        int provider1Calls2 = providerCount2.getOrDefault("20880", 0);
        int provider2Calls2 = providerCount2.getOrDefault("20881", 0);
        double ratio2 = (double) provider1Calls2 / provider2Calls2;

        System.out.println("Part 2 - Provider1 Calls: " + provider1Calls2 + ", Provider2 Calls: " + provider2Calls2 + ", Ratio: " + ratio2);

        // Assert that the ratio from the first part is less than the ratio from the second part
        Assert.assertTrue("The ratio in the first part should be less than the ratio in the second part", ratio1 < ratio2);
    }

    /**
     * Warm-up method to send a certain number of requests to stabilize the load balancer.
     *
     * @param warmUpRequests Number of warm-up requests to send.
     */
    private void warmUp(int warmUpRequests) {
        for (int i = 0; i < warmUpRequests; i++) {
            demoService.sayHello("warmup");
        }
        System.out.println("Warm-up completed with " + warmUpRequests + " requests.");
    }

    /**
     * Extracts the provider identifier (port number) from the response string.
     *
     * @param response The response string containing the provider information.
     * @return The provider identifier (port number) or "unknown" if extraction fails.
     */
    private String extractProvider(String response) {
        // Assume the response format is "Hello world, response from provider: <IP>:<PORT>"
        String[] parts = response.split(":");
        if (parts.length >= 2) {
            return parts[parts.length - 1].trim();
        } else {
            return "unknown";
        }
    }

    /**
     * Simulates a slow call to provider1 by introducing a delay.
     *
     * @param delay The delay in milliseconds to simulate a slow response.
     * @return A response string indicating the provider.
     */
    private String slowProviderCall(int delay) {
        String ipAddress = "unknown";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ipAddress = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            // Simulate a time-consuming operation, such as a remote service call or database query
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Hello world, response from provider: " + ipAddress + ":20880";
    }
}
