package org.zxc.zing.common.util;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.zxc.zing.common.constant.Constants;

import java.util.List;

/**
 * Created by xuanchen.zhao on 16-1-24.
 */
public class ZookeeperPathUtils {

    private static final int PROVIDER_INFO_NODE_DEPTH = 4;
    private static final int SERVICE_NAME_ZK_PATH_DEPTH = 3;

    public static String formatProviderPath(String serviceName, String ip, int port) {
        StringBuilder pathBuilder = new StringBuilder();

        String serviceZkPath = String.format(Constants.SERVICE_ZK_PATH_FORMAT, serviceName);
        pathBuilder.append(serviceZkPath);
        pathBuilder.append("/");

        pathBuilder.append(ip);
        pathBuilder.append(":");
        pathBuilder.append(port);

        return pathBuilder.toString();
    }

    public static boolean isServiceProviderNode(String nodePath) {
        return ZookeeperPathUtils.zkNodeDepth(nodePath) == PROVIDER_INFO_NODE_DEPTH;
    }

    public static int zkNodeDepth(String nodePath) {
        if (Strings.isNullOrEmpty(nodePath) || "/".equals(nodePath)) {
            return 0;
        }
        if (!nodePath.startsWith("/")) {
            throw new IllegalArgumentException("zookeeper path should start with /!");
        }
        String pathWithoutFirstSlash = nodePath.substring(1);
        return Iterables.size(Splitter.on(Constants.ZOOKEEPER_PATH_SEPARATOR).split(pathWithoutFirstSlash));
    }

    public static String getServiceNameFromProviderZkPath(String providerZkPath) {
        String pathWithoutFirstSlash = providerZkPath.substring(1);
        List<String> parts = Lists.newArrayList(Splitter.on(Constants.ZOOKEEPER_PATH_SEPARATOR).split(pathWithoutFirstSlash));
        return parts.get(SERVICE_NAME_ZK_PATH_DEPTH - 1);
    }

}
