package org.zxc.zing.common.registry;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.common.config.ConfigManager;
import org.zxc.zing.common.constant.Constants;
import org.zxc.zing.common.entity.ProviderInfo;
import org.zxc.zing.common.registry.function.ServerStr2ProviderInfoTransformer;
import org.zxc.zing.common.util.ZookeeperPathUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuanchen.zhao on 16-1-17.
 */
public class RegistryManager {

    private static final Logger log = LoggerFactory.getLogger(RegistryManager.class);

    private static CuratorFramework client;

    private static Executor curatorEventThreadPool = Executors.newFixedThreadPool(100);

    private static volatile boolean started = false;

    public static void start() throws Exception {
        if (!started) {
            synchronized (RegistryManager.class) {
                if (!started) {
                    String zookeeperAddress = ConfigManager.getInstance().getProperty(Constants.ZOOKEEPER_ADDRESS);

                    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
                    client = CuratorFrameworkFactory.newClient(zookeeperAddress, retryPolicy);

                    client.start();

                    TreeCache treeCache = TreeCache.newBuilder(client, Constants.SERVICE_ZK_PATH_PREFIX).setCacheData(false).build();
                    treeCache.getListenable().addListener(new ProviderNodeEventListener(), curatorEventThreadPool);
                    treeCache.start();

                    started = client.blockUntilConnected(1000, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    public static boolean publishService(String serviceName, String ip, int port) {
        if (!started) {
            throw new IllegalStateException("registry is not started.");
        }

        if (Strings.isNullOrEmpty(ip)) {
            throw new IllegalStateException("local ip address not configured yet!");
        }

        String currentServerPath = ZookeeperPathUtils.formatProviderPath(serviceName, ip, port);
        try {
            String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(currentServerPath);
            if (Strings.isNullOrEmpty(result)) {
                log.error("error when add service to zk:"+result);
                return false;
            }
            return true;
        } catch (KeeperException.NodeExistsException nee) {
            log.warn("target service "+currentServerPath+" already exists in zk");
            return true;
        } catch (Exception e) {
            log.error("error when add service to zk:"+e.getMessage(), e);
            return false;
        }
    }

    public static List<ProviderInfo> loadServerListOfService(String serviceName) {
        if (!started) {
            throw new IllegalStateException("registry is not started.");
        }

        try {
            List<String> stringList = client.getChildren().watched().forPath(String.format(Constants.SERVICE_ZK_PATH_FORMAT, serviceName));
            List<ProviderInfo> providerInfoList = Lists.transform(stringList, ServerStr2ProviderInfoTransformer.INSTANCE);
            return Lists.newArrayList(providerInfoList);
        } catch (Exception e) {
            log.error("Error when getting server list from registry:"+e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
