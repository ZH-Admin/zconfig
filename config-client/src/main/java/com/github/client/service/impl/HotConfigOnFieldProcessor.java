package com.github.client.service.impl;

import com.github.client.config.AppConfig;
import com.github.client.service.HotConfigProcessor;
import com.google.common.collect.Maps;
import com.github.common.entity.BaseResult;
import com.github.common.exception.ConfigBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Service
public class HotConfigOnFieldProcessor implements HotConfigProcessor {

    @Resource
    private AppConfig appConfig;

    @Resource(name = "configRestTemplate")
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
        LOGGER.info("app name : {} key : {}", appConfig.getName(), key);
        Map<String, String> requestParams = Maps.newHashMap();
        requestParams.put("appName", appConfig.getName());
        requestParams.put("key", key);

        return requestConfig(GET_CONFIG_URL, requestParams);
    }

    @Override
    public void updateAll() {
        LOGGER.info("Pull Hot Config");
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
            BaseResult<Map<String, String>> baseRes = restTemplate.exchange(url, HttpMethod.GET,
                    null, new ParameterizedTypeReference<BaseResult<Map<String, String>>>() {
                    }).getBody();
            result = null;
            Objects.requireNonNull(baseRes);
            if (baseRes.getCode() != 0) {
                LOGGER.error("get config from server, error code:{}, error message:{}", baseRes.getCode(), baseRes.getMessage());
            } else {
                result = baseRes.getData();
            }
        } catch (Exception e) {
            if (times == 0) {
                throw new ConfigBaseException("已重试三次, 无法获取数据");
            }
            result = retryIfFailure(url, requestParams, times - 1);
            LOGGER.error("request config", e);
        }
        return result;
    }

}
