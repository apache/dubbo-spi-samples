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

package org.apache.dubbo.samples.spi.protocol.rest.impl;

import org.apache.dubbo.samples.spi.protocol.rest.MyObject;
import org.apache.dubbo.samples.spi.protocol.rest.MyXmlObject;
import org.apache.dubbo.samples.spi.protocol.rest.api.DemoService;
import org.apache.dubbo.rpc.RpcContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public String sayJson(MyObject obj) {
        // Assuming MyObject has a method getName() that returns a String
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + obj.getName() +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + obj.getName();
    }


    @Override
    public String sayXml(MyXmlObject obj) {
        // Assuming MyXmlObject has a method getValue() that returns a String
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + obj.getValue() +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + obj.getValue();
    }


}
