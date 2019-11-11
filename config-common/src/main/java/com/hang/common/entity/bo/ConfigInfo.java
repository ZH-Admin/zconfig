package com.hang.common.entity.bo;

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

    private Integer version;

    private Map<String, String> config;

}
