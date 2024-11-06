    package org.apache.dubbo.test.serialization.protobuf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@javax.annotation.Generated(
value = "by Dubbo generator",
comments = "Source: DemoService.proto")
public interface DemoService {
static final String JAVA_SERVICE_NAME = "org.apache.dubbo.test.serialization.protobuf.DemoService";
static final String SERVICE_NAME = "demoservice.DemoService";

    // FIXME, initialize Dubbo3 stub when interface loaded, thinking of new ways doing this.
    static final boolean inited = DemoServiceDubbo.init();

    com.google.protobuf.Empty testVoid(com.google.protobuf.Empty request);

    CompletableFuture<com.google.protobuf.Empty> testVoidAsync(com.google.protobuf.Empty request);

    com.google.protobuf.StringValue testString(com.google.protobuf.StringValue request);

    CompletableFuture<com.google.protobuf.StringValue> testStringAsync(com.google.protobuf.StringValue request);

    com.google.protobuf.Int32Value testBase(com.google.protobuf.Int32Value request);

    CompletableFuture<com.google.protobuf.Int32Value> testBaseAsync(com.google.protobuf.Int32Value request);

    org.apache.dubbo.test.serialization.protobuf.BigPerson testObject(org.apache.dubbo.test.serialization.protobuf.BigPerson request);

    CompletableFuture<org.apache.dubbo.test.serialization.protobuf.BigPerson> testObjectAsync(org.apache.dubbo.test.serialization.protobuf.BigPerson request);


}
