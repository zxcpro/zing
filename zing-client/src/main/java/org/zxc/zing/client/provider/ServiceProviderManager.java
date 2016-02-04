package org.zxc.zing.client.provider;


import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.common.entity.ProviderInfo;
import org.zxc.zing.common.registry.ProviderStateListenerManager;
import org.zxc.zing.common.registry.RegistryManager;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xuanchen.zhao on 15-12-11.
 */
public class ServiceProviderManager {

    private static final Logger log = LoggerFactory.getLogger(ServiceProviderManager.class);

    private static Map<String, Set<ProviderInfo>> serviceServerMap = new ConcurrentHashMap<String, Set<ProviderInfo>>();

    static {
        try {
            bootstrap();
        } catch (Exception e) {
            log.error("client start error:"+e.getMessage(), e);
            System.exit(1);
        }
    }

    public static void bootstrap() throws Exception {
        ProviderStateListenerManager.getInstance().registerProviderStateListener(new ClientProviderStateListener());
        RegistryManager.start();
    }

    public static ProviderInfo getProvider(String serviceName) {
        Set<ProviderInfo> providerInfoSet = serviceServerMap.get(serviceName);

        if (CollectionUtils.isEmpty(providerInfoSet)) {
            throw new RuntimeException("no service provider found in registry");
        }

        List<ProviderInfo> providerInfoList = Lists.newArrayList(providerInfoSet);
        int randomIndex = new Random().nextInt(providerInfoList.size());
        return providerInfoList.get(randomIndex);
    }

    public static void initServerListOfService(String serviceName) {
        log.debug("client start load "+serviceName+" service provider list from zk");

        List<ProviderInfo> providerInfoList = RegistryManager.loadServerListOfService(serviceName);
        log.debug("client load "+serviceName+" service provider list from zk:"+providerInfoList);

        log.debug("client serviceServerMap before load:"+serviceServerMap);

        Set<ProviderInfo> providerInfoSet = new HashSet<ProviderInfo>(providerInfoList);
        serviceServerMap.put(serviceName, providerInfoSet);

        log.debug("client serviceServerMap after load:"+serviceServerMap);
    }
}
