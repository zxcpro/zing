package org.zxc.zing.demo.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zxc.zing.demo.api.DemoService;
import org.zxc.zing.demo.api.MessageDTO;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xuanchen.zhao on 15-12-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/spring/applicationContext-client.xml"} )
public class EchoClientTest{

    @Autowired
    private DemoService demoService;

    @Test
    public void test(){
        String result = demoService.echo("Hello World!");
        System.out.println(result);
    }

    @Test
    public void testLoadUser(){
        MessageDTO messageDTO = demoService.loadObject(1);
        System.out.println(messageDTO);
    }

    @Test
    public void testBatchInvocation() throws InterruptedException {
        final int THREAD_COUNT = 300;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < THREAD_COUNT; i++) {
            MockServiceInvocation invocation = new MockServiceInvocation(demoService, latch);
            threadPool.submit(invocation);
        }
        latch.await();
    }

    private static class MockServiceInvocation implements Runnable {

        private CountDownLatch latch;
        private DemoService demoService;

        private MockServiceInvocation(DemoService demoService, CountDownLatch latch) {
            this.demoService = demoService;
            this.latch = latch;
        }

        @Override
        public void run() {
            String result = demoService.echo("Hello World!");
            System.out.println(result);
            latch.countDown();
        }
    }
}
