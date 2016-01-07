package org.zxc.zing.demo.api;

import java.io.Serializable;

/**
 * Created by xuanchen.zhao on 15-12-17.
 */
public class MessageDTO implements Serializable{
    private int messageId;
    private String content;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageDTO{");
        sb.append("messageId=").append(messageId);
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
