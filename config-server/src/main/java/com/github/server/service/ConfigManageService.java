package com.github.server.service;

import java.util.List;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2019/4/6
 * *****************
 * function:
 * server端配置管理
 * key：app-code + hconfig-value
 */
public interface ConfigManageService {

    void initConfig(String key);

    void registerConfig(String appName, String key, Map<String, String> config);

    /**
     * 获取单个配置
     */
    Map<String, String> getConfig(String appName, String key);

    /**
     * 获取所有配置
     */
    Map<String, Map<String, String>> getConfigs(String appName, List<String> keys);

    void updateAllConfig(String appName, String key, Map<String, String> properties);

    /**
     * put section keys
     */
    void putSectionConfig(String appName, String key, Map<String, String> putConfig);

    /**
     * put key
     */
    void putConfig(String appName, String key, String hConfigKey, String hConfigValue);

    /**
     * 删除对应key的单个hConfigKey
     */
    void removeConfig(String appName, String key, String hConfigKey);

    /**
     * 删除对应key的部分hConfigKey
     */
    void removeSectionConfig(String appName, String key, List<String> hConfigKeys);

    /**
     * 删除对应key的全部配置
     */
    void removeAllConfig(String appName, String key);

}
