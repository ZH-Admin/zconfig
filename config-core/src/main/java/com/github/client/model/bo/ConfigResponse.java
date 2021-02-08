package com.github.client.model.bo;

import com.github.client.enums.ConfigType;
import lombok.Data;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2020/07/19 22:02
 * *****************
 * function:
 */
@Data
public class ConfigResponse extends ConfigInfo {

    private Integer version;

    private Map<String, String> content;

    private ConfigType configType;

}
