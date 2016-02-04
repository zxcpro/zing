package org.zxc.zing.client.proxy;

import com.google.common.reflect.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.client.provider.ServiceProviderManager;

/**
 * Created by xuanchen.zhao on 15-12-10.
 */
public class ServiceProxyBeanFactory {

    private static final Logger log = LoggerFactory.getLogger(ServiceProxyBeanFactory.class);


    public static Object getService(String serviceName) throws ClassNotFoundException {
        Class<?> serviceClass = Class.forName(serviceName);
        ServiceProviderManager.initServerListOfService(serviceName);
        return Reflection.newProxy(serviceClass, new ServiceProxy(serviceName));
    }
}
