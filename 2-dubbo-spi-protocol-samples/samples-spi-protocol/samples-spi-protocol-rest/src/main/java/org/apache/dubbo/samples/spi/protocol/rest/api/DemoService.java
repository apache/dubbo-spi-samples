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

package org.apache.dubbo.samples.spi.protocol.rest.api;


import org.apache.dubbo.samples.spi.protocol.rest.MyObject;
import org.apache.dubbo.samples.spi.protocol.rest.MyXmlObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/demo")
public interface DemoService {
        @POST
        @Path("/sayHello")
        String sayHello(String name);

        @POST
        @Path("/sayJson")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.TEXT_PLAIN)
        String sayJson(MyObject obj);

        @POST
        @Path("/sayXml")
        @Consumes(MediaType.TEXT_XML)
        @Produces(MediaType.TEXT_PLAIN)
        String sayXml(MyXmlObject obj);
}
