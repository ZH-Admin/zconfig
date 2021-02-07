package com.github.pojo.bo;

import lombok.Data;

/**
 * @author hangs.zhang
 * @date 2020/07/19 22:01
 * *****************
 * function:
 */
@Data
public class ConfigRequest extends ConfigInfo {

    private String appToken;

    private String profile;

    private String dataId;

    private Integer version;

}
