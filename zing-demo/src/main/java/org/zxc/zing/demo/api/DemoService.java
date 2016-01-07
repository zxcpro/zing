package org.zxc.zing.demo.api;

/**
 * Created by xuanchen.zhao on 15-12-8.
 */
public interface DemoService {
    String echo(String input);
    MessageDTO loadObject(int messageId);
}
