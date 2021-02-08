package com.github.client.model;

import lombok.Data;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2020/07/19 22:43
 * *****************
 * function:
 */
@Data
public class ConfigData {

    private String dataId;

    private Map<String, String> content;

    private Integer version;

}
