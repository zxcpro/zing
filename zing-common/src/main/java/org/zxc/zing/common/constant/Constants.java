package org.zxc.zing.common.constant;

/**
 * Created by xuanchen.zhao on 16-1-17.
 */
public class Constants {
    public final static int SERVER_DEFAULT_PORT = 4080;

    public final static String SERVER_IP_ADDRESS_CONFIG_KEY = "server.address.ip";

    public final static String SERVER_PORT_CONFIG_KEY = "server.address.port";

    public final static String ZOOKEEPER_ADDRESS = "registry.zookeeper.address";

    public final static String ZOOKEEPER_PATH_SEPARATOR = "/";

    public final static String SERVICE_ZK_PATH_PREFIX = "/zing/service";

    public final static String SERVICE_ZK_PATH_FORMAT = SERVICE_ZK_PATH_PREFIX+"/%s";
}
