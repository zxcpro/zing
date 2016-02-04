package org.zxc.zing.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by xuanchen.zhao on 16-1-17.
 */
public class ConfigManager {

    private static Logger log = LoggerFactory.getLogger(ConfigManager.class);

    private static final String EXTERNAL_CONFIG_FILE = "/data/env";
    private static final String CLASSPATH_CONFIG_FILE = "/zing.properties";


    private static volatile ConfigManager INSTANCE;

    private Properties config;

    public static ConfigManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ConfigManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConfigManager();
                }
            }
        }
        return INSTANCE;
    }

    private ConfigManager() {
        log.debug("start load config files");

        config = new Properties();
        InputStream externalConfigFileInputStream = null;
        try {
            externalConfigFileInputStream = new FileInputStream(EXTERNAL_CONFIG_FILE);
            config.load(externalConfigFileInputStream);

            log.debug("load config from "+EXTERNAL_CONFIG_FILE);
        } catch (IOException e) {
            log.error("Error when load config file:"+e.getMessage(), e);
        } finally {
            if (externalConfigFileInputStream != null) {
                try {
                    externalConfigFileInputStream.close();
                } catch (IOException e) {
                    log.error("Error when close read config file:"+e.getMessage(), e);
                }
            }
        }

        InputStream classpathConfigFileInputStream = ConfigManager.class.getResourceAsStream(CLASSPATH_CONFIG_FILE);
        if (classpathConfigFileInputStream != null) {
            try {
                config.load(classpathConfigFileInputStream);
                log.debug("load config from "+CLASSPATH_CONFIG_FILE);
            } catch (IOException e) {
                log.error("Error when load classpath config file:"+e.getMessage(), e);
            } finally {
                if (classpathConfigFileInputStream != null) {
                    try {
                        classpathConfigFileInputStream.close();
                    } catch (IOException e) {
                        log.error("Error when close classpath config file:"+e.getMessage(), e);
                    }
                }
            }
        }
    }

    public String getProperty(String key) {
        return config.getProperty(key);
    }

}
