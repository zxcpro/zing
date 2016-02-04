package org.zxc.zing.demo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.demo.api.DemoService;
import org.zxc.zing.demo.api.MessageDTO;

/**
 * Created by xuanchen.zhao on 15-12-8.
 */
public class DemoServiceImpl implements DemoService {

    private static final Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String echo(String input) {
        return "echo:"+input;
    }

    @Override
    public MessageDTO loadObject(int messageId) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(1);
        messageDTO.setContent("zxc");
        return messageDTO;
    }
}
