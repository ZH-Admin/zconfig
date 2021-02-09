package com.github.client.spring.load;

import com.github.client.annotation.HotConfig;
import com.github.client.model.ConfigInfo;
import com.github.client.utils.ConfigServerHttpClient;
import com.github.client.utils.ReflectionUtils;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Service
public class HotConfigOnFieldLoader {

    private static final ConcurrentMap<Object, Field> FIELD_MAP = Maps.newConcurrentMap();

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
