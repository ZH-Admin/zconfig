package com.github.server.entity.request;

import lombok.Data;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2019/4/7
 * *****************
 * function:
 */
@Data
public class ConfigEntity {

    private String appName;

    private String key;

    private Map<String, String> config;

}
