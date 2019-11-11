package com.hang.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function: 读取应用配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "my-app")
public class AppConfig {

    private String name;

    private String token;

}
