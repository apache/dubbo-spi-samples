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


import org.apache.dubbo.smaples.spi.serialization.BigPerson;
import org.apache.dubbo.smaples.spi.serialization.DemoService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/dubbo-demo-consumer.xml")
public class DemoServiceIT {
    @Autowired
    @Qualifier("demoService")
    private DemoService service;


    @Test
    public void testVoid() throws Exception {
        service.testVoid();
    }

    @Test
    public void testString() throws Exception {
        Assert.assertTrue(service.testString("world").endsWith("world"));
    }

    @Test
    public void testBase() throws Exception {
        Random random = new Random(10000);
        int num = random.nextInt();
        Assert.assertEquals(100 + num, service.testBase(num));
    }

    @Test
    public void testObject() throws Exception {
        BigPerson person = BigPerson.build();
        Assert.assertEquals(service.testObject(person), person);
    }
}
