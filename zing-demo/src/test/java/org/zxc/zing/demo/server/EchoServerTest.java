package org.zxc.zing.demo.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by xuanchen.zhao on 15-12-16.
 */

public class EchoServerTest {

    private static final Logger log = LoggerFactory.getLogger(EchoServerTest.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("echoserver start");
        new ClassPathXmlApplicationContext("classpath*:config/spring/applicationContext-server.xml");
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
