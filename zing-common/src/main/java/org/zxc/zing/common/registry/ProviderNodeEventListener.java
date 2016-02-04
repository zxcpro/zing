package org.zxc.zing.common.registry;

import com.google.common.base.Strings;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.common.constant.Constants;
import org.zxc.zing.common.util.ZookeeperPathUtils;


/**
 * Created by xuanchen.zhao on 16-1-17.
 */
public class ProviderNodeEventListener implements TreeCacheListener {

    private static final Logger log = LoggerFactory.getLogger(ProviderNodeEventListener.class);

    @Override
    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
        log.info("curator event received:"+event);

        if (!isServiceProviderNodeChangeEvent(event)) {
            return;
        }

        String serviceName = ZookeeperPathUtils.getServiceNameFromProviderZkPath(event.getData().getPath());
        if (Strings.isNullOrEmpty(serviceName)) {
            return;
        }

        ProviderStateListenerManager.getInstance().onProviderChange(serviceName);

    }

    private boolean isServiceProviderNodeChangeEvent(TreeCacheEvent event) {
        if (event == null || event.getData() == null) {
            return false;
        }

        String nodePath = event.getData().getPath();
        if (!nodePath.startsWith(Constants.SERVICE_ZK_PATH_PREFIX)) {
            return false;
        }

        if (!ZookeeperPathUtils.isServiceProviderNode(nodePath)) {
            return false;
        }

        if (event.getType() == TreeCacheEvent.Type.NODE_ADDED
                || event.getType() == TreeCacheEvent.Type.NODE_REMOVED) {
            return true;
        }

        return false;
    }

}
