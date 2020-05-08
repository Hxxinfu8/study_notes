package org.hx.dubbo;

import org.hx.dubbo.api.ProviderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author hx
 */
public class App {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "consumer.xml" );
        context.start();
        ProviderService providerService = (ProviderService)context.getBean( "providerService" );
        String hello = providerService.sayHello( "world" );
        System.out.println(hello);
        System.in.read();
    }
}
