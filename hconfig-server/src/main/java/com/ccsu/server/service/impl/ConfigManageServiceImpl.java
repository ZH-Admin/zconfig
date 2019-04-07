package com.ccsu.server.service.impl;

import com.ccsu.common.enums.ResultEnum;
import com.ccsu.server.exceptions.ServerException;
import com.ccsu.server.service.ConfigManageService;
import com.ccsu.server.utils.KeyUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hangs.zhang
 * @date 2019/4/6
 * *****************
 * function:
 */
@Slf4j
@Service
public class ConfigManageServiceImpl implements ConfigManageService {

    // key为app-code + hConfig-value
    private Map<String, Map<String, String>> config = Maps.newConcurrentMap();

    @Override
    public void initConfig(String key) {
        // TODO: 2019/4/6 从数据库中加载config
    }

    @Override
    public void registerConfig(String appName, String hConfigKey, Map<String, String> hConfig) {
        if (StringUtils.isBlank(hConfigKey) || Objects.isNull(hConfig)) {
            throw new ServerException(ResultEnum.PARAM_ERROR);
        }
        if (config.containsKey(KeyUtils.getRealKey(appName, hConfigKey))) {
            throw new ServerException(ResultEnum.CONFIG_KEY_IS_EXIST);
        }

        // TODO: 2019/4/6 config入库
        config.put(KeyUtils.getRealKey(appName, hConfigKey), hConfig);
    }

    @Override
    public Map<String, String> getConfig(String appName, String hConfigKey) {
        if (StringUtils.isBlank(hConfigKey) || StringUtils.isBlank(appName)) {
            throw new ServerException(ResultEnum.PARAM_ERROR);
        }
        return config.getOrDefault(KeyUtils.getRealKey(appName, hConfigKey), new ConcurrentHashMap<>());
    }

    @Override
    public Map<String, Map<String, String>> getConfigs(String appName, List<String> keys) {
        Map<String, Map<String, String>> result = Maps.newHashMap();
        keys.forEach(e -> {
            Map<String, String> hConfig = config.get(KeyUtils.getRealKey(appName, e));
            result.put(e, hConfig);
        });
        return result;
    }

    @Override
    public void updateAllConfig(String appName, String key, Map<String, String> properties) {
        if(!config.containsKey(KeyUtils.getRealKey(appName, key))) {
            throw new ServerException(ResultEnum.CONFIG_KEY_IS_NOT_EXIST);
        }
        config.put(KeyUtils.getRealKey(appName, key), properties);
    }

    @Override
    public void putSectionConfig(String appName, String key, Map<String, String> putConfig) {
        putConfig.forEach((k, v) -> putConfig(appName, key, k, v));
    }

    @Override
    public void putConfig(String appName, String key, String hConfigKey, String hConfigValue) {
        Map<String, String> map = config.get(KeyUtils.getRealKey(appName, key));
        if(Objects.isNull(map)) {
            // TODO: 2019/4/7 日志入库
            map = Maps.newHashMap();
            config.put(KeyUtils.getRealKey(appName, hConfigKey), map);
        }

        if(map.containsKey(hConfigKey)) {
            // TODO: 2019/4/7 改动日志入库
        } else {
            // TODO: 2019/4/7 变更日志入库
        }

        map.put(hConfigKey, hConfigValue);
        log.info("all config:{}", config);
        // TODO: 2019/4/7 配置入库
    }

    @Override
    public void removeConfig(String appName, String key, String hConfigKey) {

    }

    @Override
    public void removeSectionConfig(String appName, String key, List<String> hConfigKeys) {

    }

    @Override
    public void removeAllConfig(String appName, String key) {

    }
}
