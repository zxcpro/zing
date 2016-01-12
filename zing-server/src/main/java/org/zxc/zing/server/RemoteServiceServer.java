package org.zxc.zing.server;

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
import org.zxc.zing.server.remote.NettyServerHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xuanchen.zhao on 15-12-15.
 */
public class RemoteServiceServer {

    private static final Logger log = LoggerFactory.getLogger(RemoteServiceServer.class);

    private static volatile boolean started = false;

    private static ConcurrentHashMap<String, Object> serviceImplMap = new ConcurrentHashMap<String, Object>();

    static {
        bootstrap();
    }

    public static void addService(String serviceName, Object serviceImpl) {
        serviceImplMap.putIfAbsent(serviceName, serviceImpl);
    }

    public static void bootstrap() {
        log.info("try bootstrap state:"+started);
        if (!started) {
            synchronized (RemoteServiceServer.class) {
                if (!started) {
                    doStart();
                }
            }
        }
        log.info("started");
    }

    private static void doStart() {

        log.info("do start server");

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
            ChannelFuture f = bootstrap.bind(8080).sync();
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

    public static Object getActualServiceImpl(String serviceName) {
        if (!started) {
            log.warn("server not started");
            bootstrap();
        }
        return serviceImplMap.get(serviceName);
    }

}
