package org.zxc.zing.server;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.zing.common.config.ConfigManager;
import org.zxc.zing.common.constant.Constants;
import org.zxc.zing.common.registry.RegistryManager;
import org.zxc.zing.server.remote.NettyServer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xuanchen.zhao on 15-12-15.
 */
public class RemoteServiceServer {

    private static final Logger log = LoggerFactory.getLogger(RemoteServiceServer.class);

    private static volatile boolean started = false;

    private static String ipAddress = ConfigManager.getInstance().getProperty(Constants.SERVER_IP_ADDRESS_CONFIG_KEY);

    private static int port = Strings.isNullOrEmpty(ConfigManager.getInstance().getProperty(Constants.SERVER_PORT_CONFIG_KEY)) ?
                                Constants.SERVER_DEFAULT_PORT :
                                    Integer.valueOf(ConfigManager.getInstance().getProperty(Constants.SERVER_PORT_CONFIG_KEY));


    private static ConcurrentHashMap<String, Object> serviceImplMap = new ConcurrentHashMap<String, Object>();

    static {
        try {
            bootstrap();
        } catch (Exception e) {
            log.error("Server start error:"+e.getMessage(), e);
            System.exit(1);
        }
    }

    public static void addService(String serviceName, Object serviceImpl) {
        serviceImplMap.putIfAbsent(serviceName, serviceImpl);
        RegistryManager.publishService(serviceName, ipAddress, port);
    }

    public static void bootstrap() throws Exception {
        log.info("try bootstrap state:"+started);
        if (!started) {
            synchronized (RemoteServiceServer.class) {
                if (!started) {
                    doStart();
                }
            }
        }
        log.info("started");
    }

    private static void doStart() throws Exception {
        log.info("do start server");
        NettyServer.start(port);
        RegistryManager.start();
    }

    public static Object getActualServiceImpl(String serviceName) {
        return serviceImplMap.get(serviceName);
    }

}
