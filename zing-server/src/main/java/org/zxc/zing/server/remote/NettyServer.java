package org.zxc.zing.server.remote;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.common.handler.NettyDecoder;
import org.zxc.zing.common.handler.NettyEncoder;

/**
 * Created by xuanchen.zhao on 16-1-17.
 */
public class NettyServer {

    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

    private static volatile boolean started = false;

    public static void start(int port) {
        if (!started) {
            synchronized (NettyServer.class) {
                if (!started) {
                    EventLoopGroup bossGroup = new NioEventLoopGroup();
                    EventLoopGroup workerGroup = new NioEventLoopGroup();

                    ServerBootstrap bootstrap = new ServerBootstrap();
                    bootstrap.group(bossGroup, workerGroup)
                            .channel(NioServerSocketChannel.class)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(new NettyDecoder(), new NettyEncoder(), new NettyServerHandler());
                                }
                            }).option(ChannelOption.SO_BACKLOG, 128)
                            .childOption(ChannelOption.SO_KEEPALIVE, true);

                    try {
                        ChannelFuture f = bootstrap.bind(port).sync();
                        f.addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture future) throws Exception {
                                if (future.isSuccess()) {
                                    started = true;
                                    log.info("server started!");
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        log.error("server started failed:"+e.getMessage(), e);
                    }
                }
            }
        }
    }
}
