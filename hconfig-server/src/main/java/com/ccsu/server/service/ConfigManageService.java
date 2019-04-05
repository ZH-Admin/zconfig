package com.ccsu.server.service;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2019/4/6
 * *****************
 * function:
 * server端配置管理
 */
public interface ConfigManageService {

    // 生成配置
    Map<String, Map<String, String>> initConfig(String key);

    // 更新配置
    Map<String, Map<String, String>> updateConfig(String key, Map<String, String> properties);

    // 获取配置
    Map<String, Map<String, String>> getConfig(String key);

    // 删除配置
    void removeConfig(String key);

}
