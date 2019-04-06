package com.ccsu.server.service.impl;

import com.ccsu.common.enums.ResultEnum;
import com.ccsu.server.exceptions.ServerException;
import com.ccsu.server.service.ConfigManageService;
import com.ccsu.server.utils.KeyUtils;
import com.google.common.collect.Maps;
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
@Service
public class ConfigManageServiceImpl implements ConfigManageService {

    // key为app-code + hConfig-value
    private Map<String, ConcurrentHashMap<String, String>> config = Maps.newConcurrentMap();

    @Override
    public Map<String, Map<String, String>> initConfig(String key) {
        // TODO: 2019/4/6 从数据库中加载config
        return null;
    }

    @Override
    public void registerConfig(String appName, String hConfigKey, ConcurrentHashMap<String, String> hConfig) {
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
            ConcurrentHashMap<String, String> hConfig = config.get(KeyUtils.getRealKey(appName, e));
            result.put(e, hConfig);
        });
        return result;
    }

    @Override
    public void updateAllConfig(String key, Map<String, String> properties) {

    }

    @Override
    public void updateSectionConfig(String key, Map<String, String> needUpdateConfig) {

    }

    @Override
    public void updateConfig(String key, String hConfigKey, String hConfigValue) {

    }

    @Override
    public void removeConfig(String key, String hConfigKey) {

    }

    @Override
    public void removeSectionConfig(String key, List<String> hConfigKeys) {

    }

    @Override
    public void removeAllConfig(String key) {

    }
}
