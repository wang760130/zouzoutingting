package com.zouzoutingting.utils;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * Created by zhangyong on 16/6/19.
 * 配置工具
 */
public class PropManager {
    public static CompositeConfiguration config = new CompositeConfiguration();

    private PropManager() {
        try {
            config.addConfiguration(new PropertiesConfiguration(ResourceUtils
                    .getFile("classpath:common.properties")));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static PropManager getSingletonInstance() {
        return PropManagerHolderClass.PropManager;
    }

    private static class PropManagerHolderClass {
        private final static PropManager PropManager = new PropManager();
    }

    public String getProperty(String key) {
        return config.getString(key);
    }
}
