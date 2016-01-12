package org.zxc.zing.client.remote;

import com.google.common.util.concurrent.SettableFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.common.entity.RemoteResponse;

/**
 * Created by xuanchen.zhao on 15-12-13.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RemoteResponse> {

    private static final Logger log = LoggerFactory.getLogger(NettyClientHandler.class);

    private SettableFuture<RemoteResponse> future;

    public NettyClientHandler(SettableFuture<RemoteResponse> future) {
        this.future = future;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RemoteResponse msg) throws Exception {
        future.set(msg);
    }
}
