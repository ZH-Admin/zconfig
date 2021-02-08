package com.github.client.utils;

import com.github.client.exception.ConfigBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.Properties;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function: 读取应用配置
 */
public final class AppConfig {

    private static final String NAME;

    private static final String TOKEN;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    static {
        Properties property = getProperty("application.properties");
        NAME = property.getProperty("my-app.name");
        TOKEN = property.getProperty("my-app.token");
    }

    private AppConfig() {
    }

    public static String getName() {
        return NAME;
    }

    public static String getToken() {
        return TOKEN;
    }

    private static Properties getProperty(String propertyFileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL fileUrl = classloader.getResource(propertyFileName);
        Properties properties = new Properties();
        if (fileUrl != null) {
            InputStream in;
            try {
                in = fileUrl.openStream();
                properties.load(in);
                in.close();
                return properties;
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                throw new ConfigBaseException("加载资源文件失败: " + propertyFileName);
            }

        } else {
            LOGGER.error("加载资源配置文件: {} 失败，资源文件不存在", propertyFileName);
            throw new ConfigBaseException("加载资源文件失败: " + propertyFileName);
        }
    }

}
