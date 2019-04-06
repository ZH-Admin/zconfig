package com.ccsu.server.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hangs.zhang
 * @date 2019/4/6
 * *****************
 * function:
 * server端配置管理
 * key：app-code + hconfig-value
 */
public interface ConfigManageService {

    // 生成配置
    Map<String, Map<String, String>> initConfig(String key);

    // 注册配置
    void registerConfig(String appName, String key, ConcurrentHashMap<String, String> config);

    // 获取单个配置
    Map<String, String> getConfig(String appName, String key);

    // 获取配置
    Map<String, Map<String, String>> getConfigs(String appName, List<String> keys);

    // 更新所有配置
    void updateAllConfig(String key, Map<String, String> properties);

    // 更新部分配置
    void updateSectionConfig(String key, Map<String, String> needUpdateConfig);

    // 更新单个配置
    void updateConfig(String key, String hConfigKey, String hConfigValue);

    // 删除单个key
    void removeConfig(String key, String hConfigKey);

    // 删除部分key
    void removeSectionConfig(String key, List<String> hConfigKeys);

    // 删除所有配置
    void removeAllConfig(String key);

}
