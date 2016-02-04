package org.zxc.zing.client.remote;

import com.google.common.util.concurrent.SettableFuture;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.common.entity.ProviderInfo;
import org.zxc.zing.common.entity.RemoteRequest;
import org.zxc.zing.common.entity.RemoteResponse;
import org.zxc.zing.common.handler.NettyDecoder;
import org.zxc.zing.common.handler.NettyEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Created by xuanchen.zhao on 15-12-12.
 */
public class RemoteClient {

    private static final Logger log = LoggerFactory.getLogger(RemoteClient.class);

    private ProviderInfo providerInfo;

    public RemoteClient(ProviderInfo providerInfo) {
        this.providerInfo = providerInfo;
    }

    public RemoteResponse send(RemoteRequest request) throws TimeoutException, ExecutionException, InterruptedException {

        log.info("send remote request!");

        final SettableFuture<RemoteResponse> future = SettableFuture.create();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)   //客户端只有一个group
                    .channel(NioSocketChannel.class) //客户端用NioSocketChannel
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyDecoder(), new NettyEncoder(), new NettyClientHandler(future));
                        }
                    });

            ChannelFuture f = bootstrap.connect(providerInfo.getAddress(), providerInfo.getPort()).sync();
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("client connect success!");
                }
            });
            ChannelFuture writeFuture = f.channel().writeAndFlush(request);
            writeFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("client write complete!");
                }
            });

            return future.get(1000, TimeUnit.MILLISECONDS);
        } finally {
            bossGroup.shutdownGracefully();
        }
    }

}
