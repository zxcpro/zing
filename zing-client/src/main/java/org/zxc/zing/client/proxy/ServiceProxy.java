package org.zxc.zing.client.proxy;

import com.google.common.reflect.AbstractInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.client.provider.ProviderInfo;
import org.zxc.zing.client.provider.ServiceProviderManager;
import org.zxc.zing.client.remote.RemoteClient;
import org.zxc.zing.common.entity.RemoteRequest;
import org.zxc.zing.common.entity.RemoteResponse;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by xuanchen.zhao on 15-12-8.
 */
public class ServiceProxy extends AbstractInvocationHandler{

    private static final Logger log = LoggerFactory.getLogger(ServiceProxy.class);

    private String serviceName;

    public ServiceProxy(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("proxy method invocation!");

        ProviderInfo provider = ServiceProviderManager.getProvider(serviceName);

        RemoteRequest request = new RemoteRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setServiceName(serviceName);
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setArguments(args);

        //todo netty client是一个客户端和一个服务端的连接，应该是一次建立，之后到该服务器的连接可以复用
        RemoteClient client = new RemoteClient(provider);
        RemoteResponse response = client.send(request);

        return response.getResponseValue();
    }
}
