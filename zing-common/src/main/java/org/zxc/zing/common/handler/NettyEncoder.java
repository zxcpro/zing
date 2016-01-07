package org.zxc.zing.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.common.serialize.Serializer;

/**
 * Created by xuanchen.zhao on 15-12-15.
 */
public class NettyEncoder extends MessageToByteEncoder {

    private static final Logger log = LoggerFactory.getLogger(NettyEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

        log.info("encoding start");

        byte[] bytes = Serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);

        log.info("msg encoded length:"+bytes.length);

    }
}
