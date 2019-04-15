package com.hang.client.service.impl;

import com.hang.client.config.AppConfig;
import com.hang.client.service.HotConfigOnFieldManager;
import com.hang.common.entity.BaseRes;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Slf4j
@Service
public class HotConfigOnFieldManagerImpl implements HotConfigOnFieldManager {

    private AppConfig appConfig;

    private RestTemplate restTemplate;

    private static final String GET_CONFIG_URL = "http://127.0.0.1:8090/config?appName={appName}&key={key}";

    // key为beanName+configName，value为真正的config配置
    private volatile Map<String, Map<String, String>> configMap = new ConcurrentHashMap<>();

    @Autowired
    public HotConfigOnFieldManagerImpl(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public void setConfig(String key, Map<String, String> newConfig) {
        Map<String, String> oldConfig = configMap.get(key);
        if(Objects.isNull(oldConfig)) {
            configMap.put(key, newConfig);
        } else {
            oldConfig.clear();
            oldConfig.putAll(newConfig);
        }
    }

    @Override
    public void getConfig(String key) {
        configMap.get(key);
    }

    @Override
    public Map<String, String> getConfigFromRemote(String key) {
        log.info("app name : {}\tkey : {}", appConfig.getName(), key);
        // TODO: 2018/8/27 请求server，key为appName+hConfig.value  更新config
        Map<String, String> map = Maps.newHashMap();
        map.put("appName", appConfig.getName());
        map.put("key", key);
        BaseRes baseRes = restTemplate.getForObject(GET_CONFIG_URL, BaseRes.class, map);
        // TODO: 2019/4/7 判断code 失败重试三次
        log.info("body:{}", baseRes);
        return baseRes.getData() == null ? Maps.newHashMap() : (Map) baseRes.getData();
    }

    @Override
    public void updateAll() {
        // TODO: 2019/4/6 更新所有
        log.info("Pull Hot Config");
        configMap.forEach((k, v) -> updateByKey(k));
    }

    @Override
    public void updateByKey(String key) {
        // TODO: 2019/4/6 根据key更新本地配置
        Map<String, String> map = getConfigFromRemote(key);
        setConfig(key, map);
    }

}
