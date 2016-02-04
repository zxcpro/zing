package org.zxc.zing.client.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.common.registry.ProviderStateListener;

/**
 * Created by xuanchen.zhao on 16-1-20.
 */
public class ClientProviderStateListener implements ProviderStateListener {

    private static final Logger log = LoggerFactory.getLogger(ClientProviderStateListener.class);

    @Override
    public void onProviderChange(String serviceName) {
        log.info("client event listener received changed service:"+serviceName);
        ServiceProviderManager.initServerListOfService(serviceName);
    }
}
