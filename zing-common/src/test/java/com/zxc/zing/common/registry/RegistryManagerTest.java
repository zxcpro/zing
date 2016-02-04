package com.zxc.zing.common.registry;

import org.zxc.zing.common.entity.ProviderInfo;
import org.zxc.zing.common.registry.RegistryManager;

import java.util.List;

/**
 * Created by xuanchen.zhao on 16-1-17.
 */
public class RegistryManagerTest {

    public static void main(String[] args) throws Exception {
        RegistryManager.start();
        RegistryManager.publishService("org.zxc.zing.demo.api.DemoService", "127.0.0.1", 4080);
        List<ProviderInfo> providerInfos = RegistryManager.loadServerListOfService("org.zxc.zing.demo.api.DemoService");
    }
}
