package com.github.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.exception.ConfigBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2019/11/11 14:14
 * *****************
 * function:
 */
public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String JSON_ERROR_FORMAT = "Json 解析异常,Json:{}";

    private static final String JSON_ERROR_MESSAGE = "Json解析异常";

    static {
        FilterProvider theFilter = new SimpleFilterProvider().addFilter("outFilter", SimpleBeanPropertyFilter.serializeAllExcept());
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 当bean没有属性不报错
        OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 主要用于日志答应输出全部
        OBJECT_MAPPER.setFilterProvider(theFilter);
    }

    private JsonUtils() {
    }

    public static String getJsonNodeContent(String json, String nodeName) {
        try {
            return OBJECT_MAPPER.readTree(json).get(nodeName).toString();
        } catch (IOException e) {
            LOGGER.error(JSON_ERROR_FORMAT, json);
            throw new ConfigBaseException(JSON_ERROR_MESSAGE);
        }
    }

    public static String toJson(Object o) {
        if (o != null && !(o instanceof String)) {
            try {
                return OBJECT_MAPPER.writeValueAsString(o);
            } catch (Exception e) {
                throw new ConfigBaseException(JSON_ERROR_MESSAGE);
            }
        }
        return (String) o;
    }

    public static <T> T fromJson(String json, Class<T> type) {
        T rst = null;
        try {
            rst = OBJECT_MAPPER.readValue(json, type);
            return rst;
        } catch (Exception e) {
            LOGGER.error(JSON_ERROR_FORMAT, json);
            throw new ConfigBaseException(JSON_ERROR_MESSAGE);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeRef) {
        T rst = null;
        try {
            rst = OBJECT_MAPPER.readValue(json, typeRef);
            return rst;
        } catch (Exception e) {
            LOGGER.error(JSON_ERROR_FORMAT, json);
            throw new ConfigBaseException(JSON_ERROR_MESSAGE);
        }
    }

}
