package com.github.client.config;

import com.github.common.exception.ConfigBaseException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
@Getter
@Component
public final class AppConfig {

    private String name;

    private String token;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @PostConstruct
    public void init() {
        Properties property = getProperty("application.properties");
        this.name = property.getProperty("my-app.name");
        this.token = property.getProperty("my-app.token");
    }

    public static Properties getProperty(String propertyFileName) {
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
            LOGGER.error("加载资源配置文件:" + propertyFileName + "失败，资源文件不存在");
            throw new ConfigBaseException("加载资源文件失败: " + propertyFileName);
        }
    }

}
