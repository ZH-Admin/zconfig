package com.ccsu.client.handler;

import com.ccsu.client.annotation.HConfig;
import com.ccsu.client.service.HotConfigOnFieldManager;
import com.ccsu.client.utils.CommonUtils;
import com.ccsu.client.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hangs.zhang
 * @date 2018/8/14
 * *********************
 * function:
 * BeanPostProcessor接口：
 *  Spring的后置处理器，在IoC完成bean实例化、配置以及其他初始化方法前后要添加一些自己逻辑处理
 *
 * HConfigOnFieldBeanPostProcessor：
 *  在客户端初始化bean之前，生成配置（从hotConfigManager中获取）
 */
@SuppressWarnings("all")
@Component
@Slf4j
public class HConfigOnFieldBeanPostProcessor implements BeanPostProcessor {

    private HotConfigOnFieldManager hotConfigManager;

    @Autowired
    public HConfigOnFieldBeanPostProcessor(HotConfigOnFieldManager hotConfigManager) {
        this.hotConfigManager = hotConfigManager;
    }

    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("postProcessBeforeInitialization, beanName:{}", beanName);

        List<Field> fields = ReflectionUtils.getFieldsWithAnnotation(bean, HConfig.class);
        for (Field field : fields) {
            HConfig hConfig = field.getAnnotation(HConfig.class);
            log.info("HConfig value : {}", hConfig.value());
            // 初始化data
            ReflectionUtils.setFieldContent(bean, field, new ConcurrentHashMap<>());
            // 获取对象
            Object obj = ReflectionUtils.getFieldContent(bean, field);
            if (obj instanceof Map) {
                // 线程安全
                Map<String, String> data = (ConcurrentHashMap<String, String>) obj;
                Map<String, String> dataFromRemote = hotConfigManager
                        .getConfigFromRemote(CommonUtils.getRealKey(beanName, hConfig.value()));
                // 完成远程配置的读取
                data.putAll(dataFromRemote);
                hotConfigManager.setConfig(CommonUtils.getRealKey(beanName, hConfig.value()), data);
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
