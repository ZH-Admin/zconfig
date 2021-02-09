package com.github.client.spring;

import com.github.client.annotation.HotConfig;
import com.github.client.model.ConfigInfo;
import com.github.client.utils.ConfigServerHttpClient;
import com.github.client.exception.ConfigBaseException;
import com.github.client.utils.ReflectionUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author hangs.zhang
 * @date 2018/8/14
 * *********************
 * function:
 * BeanPostProcessor接口: Spring的后置处理器，在IoC完成bean实例化、配置以及其他初始化方法前后要添加一些自己逻辑处理
 * HotConfigBeanPostProcessor: 在客户端初始化bean过程中，生成配置（从hotConfigManager中获取）
 */
@Component
public class ConfigBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private FieldLoader fieldLoader;

    private static final Map<String, ConfigInfo> DATA_MAP = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @PostConstruct
    public void init() {
        List<ConfigInfo> config = ConfigServerHttpClient.getConfig();
        Map<String, ConfigInfo> collect = config.stream().collect(Collectors.toMap(ConfigInfo::getDataId, configInfo -> configInfo));
        DATA_MAP.putAll(collect);
    }

    @Override
    public Object postProcessBeforeInitialization(@Nonnull Object bean, @Nonnull String beanName) {
        List<Field> fields = ReflectionUtils.getFieldsWithAnnotation(bean, HotConfig.class);
        for (Field field : fields) {
            HotConfig hConfig = field.getAnnotation(HotConfig.class);
            LOGGER.debug("HConfig value : {}", hConfig.value());
            // 初始化data
            ReflectionUtils.setFieldContent(bean, field, Maps.newConcurrentMap());
            // 获取对象
            Object fieldValue = ReflectionUtils.getFieldContent(bean, field);
            if (fieldValue instanceof Map) {
                // 线程安全
                @SuppressWarnings("unchecked")
                Map<String, String> data = (ConcurrentHashMap<String, String>) fieldValue;
                // 完成远程配置的读取
                ConfigInfo configInfo = DATA_MAP.get(hConfig.value());
                if (!Objects.isNull(configInfo)) {
                    Map<String, String> content = configInfo.getContent();
                    if (!Objects.isNull(content)) {
                        data.putAll(content);
                    }
                }
                fieldLoader.storeField(bean, field);
            } else {
                throw new ConfigBaseException("not suppoert this type : " + fieldValue.getClass());
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) {
        return bean;
    }

}
