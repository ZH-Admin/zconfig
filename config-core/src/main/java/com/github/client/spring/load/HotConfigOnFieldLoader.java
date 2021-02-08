package com.github.client.spring.load;

import com.github.client.annotation.HotConfig;
import com.github.client.model.ConfigData;
import com.github.client.model.bo.ConfigResponse;
import com.github.client.utils.ReflectionUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentMap;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Service
public class HotConfigOnFieldLoader {

    @Resource
    private RemoteConfigLoader remoteConfigProcessor;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final ConcurrentMap<Object, Field> CONFIGS = Maps.newConcurrentMap();

    public void putConfig2Field(Object bean, Field field) {
        String dataId = field.getAnnotation(HotConfig.class).value();
        ConfigData config = getConfig(dataId);
        ReflectionUtils.setFieldContent(bean, field, config.getContent());
        CONFIGS.put(bean, field);
    }

    public ConfigData getConfig(String dataId) {
        LOGGER.info("dataId {} getConfig", dataId);
        ConfigResponse configResponse = remoteConfigProcessor.getConfig(dataId);
        ConfigData configData = new ConfigData();
        BeanUtils.copyProperties(configResponse, configData);
        return configData;
    }

    public void processAll() {
        CONFIGS.forEach(this::putConfig2Field);
    }

}
