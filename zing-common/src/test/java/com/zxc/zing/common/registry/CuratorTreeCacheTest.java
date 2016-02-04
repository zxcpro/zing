package com.zxc.zing.common.registry;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by xuanchen.zhao on 16-1-22.
 */
public class CuratorTreeCacheTest {

    public static void main(String[] args) throws Exception {
        String path = "/zing/service";

        String zkAddress = "54.200.107.196:2181";
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, policy);

        client.start();
        client.blockUntilConnected();

        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        TreeCache treeCache = new TreeCache(client, path);
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                System.out.println(event);
            }
        }, threadPool);
        treeCache.start();

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
