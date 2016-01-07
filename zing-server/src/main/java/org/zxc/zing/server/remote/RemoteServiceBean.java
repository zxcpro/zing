package org.zxc.zing.server.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.server.RemoteServiceServer;

/**
 * Created by xuanchen.zhao on 15-12-16.
 */
public class RemoteServiceBean {

    private static final Logger log = LoggerFactory.getLogger(RemoteServiceBean.class);

    private String serviceName;
    private Object serviceImpl;

    public void init() {
        log.info("spring add serviceBean init");
        RemoteServiceServer.addService(serviceName, serviceImpl);
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(Object serviceImpl) {
        this.serviceImpl = serviceImpl;
    }
}
