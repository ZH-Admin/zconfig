package com.github.server.service.impl;

import com.github.common.pojo.bo.ConfigInfo;
import com.github.common.enums.ResultEnum;
import com.github.server.exceptions.ConfigServerException;
import com.github.server.exceptions.Verify;
import com.github.server.service.ConfigManageService;
import com.github.server.service.KafkaMessageService;
import com.github.server.utils.KeyUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * key为app-code + hConfig-value
     */
    private Map<String, Map<String, String>> config = Maps.newConcurrentMap();

    @Autowired
    private KafkaMessageService kafkaMessageService;

    @Override
    public void initConfig(String key) {
        // TODO: 2019/4/6 从数据库中加载config
    }

    @Override
    public void registerConfig(String appName, String hConfigKey, Map<String, String> hConfig) {
        Verify.verifyNotNull(ResultEnum.PARAM_ERROR, appName, hConfigKey, hConfig);
        Verify.verify(!config.containsKey(KeyUtils.getRealKey(appName, hConfigKey)), ResultEnum.CONFIG_KEY_IS_EXIST);

        // TODO: 2019/4/6 config入库
        config.put(KeyUtils.getRealKey(appName, hConfigKey), hConfig);
        // 主动通知客户端
        kafkaMessageService.sendMessage(appName, "");
    }

    @Override
    public Map<String, String> getConfig(String appName, String hConfigKey) {
        ConfigInfo configInfo = new ConfigInfo();
        if (StringUtils.isBlank(hConfigKey) || StringUtils.isBlank(appName)) {
            throw new ConfigServerException(ResultEnum.PARAM_ERROR);
        }
        Map<String, String> configMap = this.config.getOrDefault(KeyUtils.getRealKey(appName, hConfigKey), new ConcurrentHashMap<>());
        return configMap;
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
        if (!config.containsKey(KeyUtils.getRealKey(appName, key))) {
            throw new ConfigServerException(ResultEnum.CONFIG_KEY_IS_NOT_EXIST);
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
        if (Objects.isNull(map)) {
            // TODO: 2019/4/7 日志入库
            map = Maps.newHashMap();
            config.put(KeyUtils.getRealKey(appName, hConfigKey), map);
        }

        if (map.containsKey(hConfigKey)) {
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
