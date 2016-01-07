package org.zxc.zing.client.provider;

/**
 * Created by xuanchen.zhao on 15-12-11.
 */
public class ServiceProviderManager {

    public static ProviderInfo getProvider(String serviceName) {
        return new ProviderInfo("127.0.0.1", 8080);
    }
}
