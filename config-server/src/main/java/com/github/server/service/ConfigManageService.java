package com.github.server.service;

import com.github.server.entity.po.PropertiesPO;

/**
 * @author hangs.zhang
 * @date 2019/4/6
 * *****************
 * function:
 * server端配置管理
 * key：app-code + hconfig-value
 */
public interface ConfigManageService {

    void initConfig();

    /**
     * 获取单个配置
     */
    PropertiesPO getConfig(String appName, String key);

}
