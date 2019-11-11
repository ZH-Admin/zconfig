package com.hang.client.handler;

import com.hang.client.annotation.HConfig;
import com.hang.client.service.HotConfigProcessor;
import com.hang.client.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hangs.zhang
 * @date 2018/8/14
 * *********************
 * function:
 * BeanPostProcessor接口: Spring的后置处理器，在IoC完成bean实例化、配置以及其他初始化方法前后要添加一些自己逻辑处理
 * HConfigOnFieldBeanPostProcessor: 在客户端初始化bean过程中，生成配置（从hotConfigManager中获取）
 */
@Component
@Slf4j
public class HConfigOnFieldBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private HotConfigProcessor hotConfigProcessor;

    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.debug("HConfigPostProcess BeforeInitialization, beanName:{}", beanName);

        List<Field> fields = ReflectionUtils.getFieldsWithAnnotation(bean, HConfig.class);
        for (Field field : fields) {
            HConfig hConfig = field.getAnnotation(HConfig.class);
            log.debug("HConfig value : {}", hConfig.value());
            // 初始化data
            ReflectionUtils.setFieldContent(bean, field, new ConcurrentHashMap<>(16));
            // 获取对象
            Object obj = ReflectionUtils.getFieldContent(bean, field);
            if (obj instanceof Map) {
                // 线程安全
                Map<String, String> data = (ConcurrentHashMap<String, String>) obj;
                // 完成远程配置的读取
                Map<String, String> dataFromRemote = hotConfigProcessor.getConfigFromRemote(hConfig.value());
                data.putAll(dataFromRemote);
                hotConfigProcessor.setConfig(hConfig.value(), data);
            }
        }
        return bean;
    }


    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
