package org.zxc.zing.server.remote;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.common.entity.RemoteRequest;
import org.zxc.zing.common.entity.RemoteResponse;
import org.zxc.zing.server.RemoteServiceServer;

import java.lang.reflect.Method;

/**
 * Created by xuanchen.zhao on 15-12-16.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RemoteRequest> {

    private static final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RemoteRequest request) throws Exception {

        log.info("current handler:"+super.toString()+" server received request:"+request);

        Object actualServiceImpl = RemoteServiceServer.getActualServiceImpl(request.getServiceName());

        log.info("server get serviceImpl from map:"+actualServiceImpl+" hashCode:"+((Object)actualServiceImpl).toString());

        if (actualServiceImpl != null) {
            RemoteResponse response = new RemoteResponse();
            response.setRequestId(request.getRequestId());
            Class<?> serviceInterface = actualServiceImpl.getClass();
            Method method = serviceInterface.getMethod(request.getMethodName(), request.getParameterTypes());
            Object result = method.invoke(actualServiceImpl, request.getArguments());

            log.info("get result from server:"+result);

            response.setResponseValue(result);

            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("server write back success");
                }
            });


        }
    }
}
