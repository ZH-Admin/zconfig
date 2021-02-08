package com.github.client.spring.load;

import com.github.client.annotation.HConfig;
import com.github.client.model.ConfigData;
import com.github.client.enums.ConfigType;
import com.github.client.exception.ConfigBaseException;
import com.github.client.model.bo.ConfigResponse;
import com.github.client.utils.JsonUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Service
public class HotConfigOnFieldProcessor {

    @Resource
    private RemoteConfigProcessor remoteConfigProcessor;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Map<Object, Set<Field>> beanFieldMap = Maps.newHashMap();

    /**
     * bean,dataId,config
     */
    private final Table<Object, Field, ConfigData> TABLES = Tables.newCustomTable(Maps.newLinkedHashMap(), () -> Maps.newLinkedHashMap());

    private final Map<String, ConfigData> CONFIG_MAP = new ConcurrentHashMap<>();

    public void process(Object bean, Field field) {
        if (!beanFieldMap.containsKey(bean)) {
            beanFieldMap.put(bean, Sets.newHashSet(field));
        } else {
            Set<Field> fields = beanFieldMap.get(bean);
            fields.add(field);
        }

        if (!TABLES.contains(bean, field)) {
            putConfig2Field(bean, field);
        } else {
            ConfigData configData = TABLES.get(bean, field);
            if (needUpdate(configData.getDataId(), configData.getVersion())) {
                putConfig2Field(bean, field);
            }
        }
    }

    public boolean needUpdate(String dataId, Integer version) {
        return version == null || remoteConfigProcessor.checkUpdate(dataId, version);
    }

    public void putConfig2Field(Object bean, Field field) {
        String dataId = field.getAnnotation(HConfig.class).value();
        ConfigData config = getConfig(dataId);
        field.setAccessible(true);
        try {
            field.set(bean, JsonUtils.fromJson(config.getContent(), Map.class));
            TABLES.put(bean, field, config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ConfigData getConfig(String dataId) {
        LOGGER.info("dataId {} getConfig", dataId);
        ConfigResponse configResponse = remoteConfigProcessor.getConfig(dataId);
        if (Objects.equals(configResponse.getConfigType(), ConfigType.PROPERTIES)) {
            ConfigData configData = new ConfigData();
            BeanUtils.copyProperties(configResponse, configData);
            return configData;
        }
        throw new ConfigBaseException("类型错误");
    }

    public void processAll() {
        TABLES.rowKeySet().forEach(bean -> {
            Map<Field, ConfigData> dataMap = TABLES.row(bean);
            dataMap.forEach((field, configData) -> {
                if (needUpdate(getDataId(field), configData.getVersion())) {
                    putConfig2Field(bean, field);
                } else {
                    LOGGER.debug("dataId对应的数据已经是最新");
                }
            });
        });
    }

    public static String getDataId(Field field) {
        return field.getAnnotation(HConfig.class).value();
    }

}
