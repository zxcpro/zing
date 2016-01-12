package org.zxc.zing.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.common.serialize.Serializer;

import java.util.List;

/**
 * Created by xuanchen.zhao on 15-12-15.
 */
public class NettyDecoder extends ByteToMessageDecoder{

    private static final Logger log = LoggerFactory.getLogger(NettyDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        log.info("try decode:"+in.toString());

        if (in.readableBytes() < 4) {
            log.info("no enough readable bytes");
            return;
        }

        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }

        log.info("try decode data length:"+dataLength);

        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
        }

        log.info("try decode doDecode");

        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object deserialized = Serializer.deserialize(data);
        out.add(deserialized);

    }
}
