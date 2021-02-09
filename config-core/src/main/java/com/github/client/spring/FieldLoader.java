package com.github.client.spring;

import com.github.client.annotation.HotConfig;
import com.github.client.model.ConfigInfo;
import com.github.client.utils.ConfigServerHttpClient;
import com.github.client.utils.ReflectionUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Component
public class FieldLoader {

    private static final ConcurrentMap<Object, Field> FIELD_MAP = Maps.newConcurrentMap();

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init() {
        LOGGER.info("config schedule init");
        SCHEDULED_EXECUTOR_SERVICE.schedule(() -> {
            while (true) {
                try {
                    // long pull, 长轮询
                    LOGGER.info("start pull");
                    if (ConfigServerHttpClient.checkConfigUpdate()) {
                        processAll();
                    }
                    LOGGER.info("end pull");
                } catch (Exception e) {
                    LOGGER.error("long pull error", e);
                }
            }
        }, 60, TimeUnit.SECONDS);
    }

    public void storeField(Object bean, Field field) {
        FIELD_MAP.put(bean, field);
    }

    public void processAll() {
        List<ConfigInfo> config = ConfigServerHttpClient.getConfig();
        if (!CollectionUtils.isEmpty(config)) {
            Map<String, ConfigInfo> configMap = config.stream().collect(Collectors.toMap(ConfigInfo::getDataId, configInfo -> configInfo));
            FIELD_MAP.forEach((bean, field) -> {
                ConfigInfo configInfo = configMap.get(field.getAnnotation(HotConfig.class).value());
                if (configInfo != null) {
                    ReflectionUtils.setFieldContent(bean, field, configInfo.getContent());
                }
            });

        }
    }

}
