package org.apache.dubbo.samples.spi.metadata;

import org.apache.dubbo.samples.spi.metadata.api.DemoService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 其一
 * @date 2024/6/17
 */
public class EtcdConsumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/metadata-consumer.xml"});
        context.start();
        DemoService demoService = context.getBean("demoService", DemoService.class);

        String hello = demoService.sayHello("world");
        System.out.println(hello);
    }
}
