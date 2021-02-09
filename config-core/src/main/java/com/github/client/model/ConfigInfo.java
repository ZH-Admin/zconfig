package com.github.client.model;

import lombok.Data;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2019/11/11 19:55
 * *****************
 * function:
 */
@Data
public class ConfigInfo {

    private String appName;

    private String dataId;

    private Map<String, String> content;

    private Integer dataVersion;

    private Integer appVersion;

}
