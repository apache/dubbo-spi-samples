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


import org.apache.dubbo.samples.spi.protocol.rest.MyObject;
import org.apache.dubbo.samples.spi.protocol.rest.MyXmlObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class DemoServiceIT {

    protected static final String HOST = System.getProperty("zookeeper.address", "localhost");

    protected static String toUri(String path) {
        return "http://" + HOST + ":20880/" + path;
    }

    @Test
    public void test() {
        RestTemplate restTemplate = new RestTemplate();

        // Create form data
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("number", "world");

        // Set the request header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // create a request entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

        // send a post request
        ResponseEntity<String> response = restTemplate.postForEntity(toUri("demo/sayHello"), requestEntity, String.class);

        System.out.println("Response: " + response.getBody());
        Assert.assertTrue(response.getBody().startsWith("Hello world"));
    }

    @Test
    public void testPostJson() {
        RestTemplate restTemplate = new RestTemplate();

        MyObject myObject = new MyObject();
        myObject.setName("world");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MyObject> requestEntity = new HttpEntity<>(myObject, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(toUri("demo/sayJson"), requestEntity, String.class);

        System.out.println("Response: " + response.getBody());
        Assert.assertTrue(response.getBody().contains("Hello world"));
    }


    @Test
    public void testPostXml() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(
                new Jaxb2RootElementHttpMessageConverter(),
                new StringHttpMessageConverter()
        ));

        MyXmlObject xmlObject = new MyXmlObject();
        xmlObject.setValue("world");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        HttpEntity<MyXmlObject> requestEntity = new HttpEntity<>(xmlObject, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(toUri("demo/sayXml"), requestEntity, String.class);

        System.out.println("Response: " + response.getBody());
        Assert.assertTrue(response.getBody().contains("Hello world"));
    }


}
