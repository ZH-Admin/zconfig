package com.hang.client.service.impl;

import com.hang.client.config.AppConfig;
import com.hang.client.service.HotConfigProcessor;
import com.hang.common.entity.BaseRes;
import com.google.common.collect.Maps;
import com.hang.common.exception.ConfigBaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
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
public class HotConfigOnFieldProcessor implements HotConfigProcessor {

    @Resource
    private AppConfig appConfig;

    @Resource(name = "configRestTemplate")
    private RestTemplate restTemplate;

    private static final String GET_CONFIG_URL = "http://127.0.0.1:8090/config?appName={appName}&key={key}";

    /**
     * key为beanName+configName，value为真正的config配置
     */
    private Map<String, Map<String, String>> configMap = new ConcurrentHashMap<>();

    @Override
    public void setConfig(String key, Map<String, String> newConfig) {
        Map<String, String> oldConfig = configMap.get(key);
        if (Objects.isNull(oldConfig)) {
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
        Map<String, String> requestParams = Maps.newHashMap();
        requestParams.put("appName", appConfig.getName());
        requestParams.put("key", key);

        Map<String, String> config = requestConfig(GET_CONFIG_URL, requestParams);
        return config;
    }

    @Override
    public void updateAll() {
        log.info("Pull Hot Config");
        configMap.forEach((k, v) -> updateByKey(k));
    }

    @Override
    public void updateByKey(String key) {
        Map<String, String> map = getConfigFromRemote(key);
        setConfig(key, map);
    }

    private Map<String, String> requestConfig(String url, Map<String, String> requestParams) {
        return retryIfFailure(url, requestParams, 3);
    }

    private Map<String, String> retryIfFailure(String url, Map<String, String> requestParams, int times) {
        Map<String, String> result;
        try {
            BaseRes<Map<String, String>> baseRes = restTemplate.exchange(url, HttpMethod.GET,
                    null, new ParameterizedTypeReference<BaseRes<Map<String, String>>>() {
                    }).getBody();
            result = null;
            Objects.requireNonNull(baseRes);
            if (baseRes.getCode() != 0) {
                log.error("get config from server, error code:{}, error message:{}", baseRes.getCode(), baseRes.getMessage());
            } else {
                result = baseRes.getData();
            }
        } catch (Exception e) {
            if (times == 0) {
                throw new ConfigBaseException("已重试三次, 无法获取数据");
            }
            result = retryIfFailure(url, requestParams, times - 1);
            log.error("request config", e);
        }
        return result;
    }

}
