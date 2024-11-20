    package org.apache.dubbo.test.serialization.protobuf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@javax.annotation.Generated(
value = "by Dubbo generator",
comments = "Source: DemoService.proto")
public final class DemoServiceDubbo {
private static final AtomicBoolean registered = new AtomicBoolean();

public static boolean init() {
    if (registered.compareAndSet(false, true)) {
            org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
            com.google.protobuf.Empty.getDefaultInstance());
            org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
            com.google.protobuf.Int32Value.getDefaultInstance());
            org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
            com.google.protobuf.StringValue.getDefaultInstance());
            org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
            org.apache.dubbo.test.serialization.protobuf.BigPerson.getDefaultInstance());
    }
    return true;
}

private DemoServiceDubbo() {}

}
