package com.github.client.spring;

import com.github.client.annotation.HotConfig;
import com.github.client.model.ConfigData;
import com.github.client.spring.load.HotConfigOnFieldLoader;
import com.github.client.utils.ReflectionUtils;
import com.github.client.exception.ConfigBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hangs.zhang
 * @date 2018/8/14
 * *********************
 * function:
 * BeanPostProcessor接口: Spring的后置处理器，在IoC完成bean实例化、配置以及其他初始化方法前后要添加一些自己逻辑处理
 * HotConfigBeanPostProcessor: 在客户端初始化bean过程中，生成配置（从hotConfigManager中获取）
 */
@Component
public class HotConfigBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private HotConfigOnFieldLoader hotConfigProcessor;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Object postProcessBeforeInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        List<Field> fields = ReflectionUtils.getFieldsWithAnnotation(bean, HotConfig.class);
        for (Field field : fields) {
            HotConfig hConfig = field.getAnnotation(HotConfig.class);
            LOGGER.debug("HConfig value : {}", hConfig.value());
            // 初始化data
            ReflectionUtils.setFieldContent(bean, field, new ConcurrentHashMap<>(16));
            // 获取对象
            Object obj = ReflectionUtils.getFieldContent(bean, field);
            if (obj instanceof Map) {
                // 线程安全
                @SuppressWarnings("unchecked")
                Map<String, String> data = (ConcurrentHashMap<String, String>) obj;
                // 完成远程配置的读取
                ConfigData config = hotConfigProcessor.getConfig(hConfig.value());
                if (!Objects.isNull(config)) {
                    Map<String, String> content = config.getContent();
                    if (!Objects.isNull(content)) {
                        data.putAll(content);
                    }
                }
            } else {
                throw new ConfigBaseException("not suppoert this type : " + obj.getClass());
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        return bean;
    }

}
