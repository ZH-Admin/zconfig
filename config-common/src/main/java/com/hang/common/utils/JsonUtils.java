package com.hang.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.hang.common.exception.ConfigBaseException;
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

    private static ObjectMapper mapper = new ObjectMapper();

    private static ObjectMapper underline2Hump = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String JSON_ERROR_FORMAT = "Json 解析异常,Json:{}";

    private static final String JSON_ERROR_MESSAGE = "Json解析异常";

    static {
        FilterProvider theFilter = new SimpleFilterProvider().addFilter("outFilter", SimpleBeanPropertyFilter.serializeAllExcept());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 当bean没有属性不报错
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 主要用于日志答应输出全部
        mapper.setFilterProvider(theFilter);
        mapper.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);

        //**********************************************************************//

        underline2Hump.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        underline2Hump.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        underline2Hump.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //当bean没有属性不报错
        underline2Hump.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 主要用于日志答应输出全部
        underline2Hump.setFilterProvider(theFilter);
        underline2Hump.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    private JsonUtils() {
    }

    public static String getJsonNodeContent(String json, String nodeName) {
        try {
            return mapper.readTree(json).get(nodeName).toString();
        } catch (IOException e) {
            LOGGER.error(JSON_ERROR_FORMAT, json);
            throw new ConfigBaseException(JSON_ERROR_MESSAGE);
        }
    }

    public static String toJson(Object o) {
        if (o != null && !(o instanceof String)) {
            try {
                return mapper.writeValueAsString(o);
            } catch (Exception e) {
                throw new ConfigBaseException(JSON_ERROR_MESSAGE);
            }
        }
        return (String) o;
    }

    public static <T> T fromJson(String json, Class<T> type) {
        Object rst = null;
        try {
            rst = mapper.readValue(json, type);
            return (T) rst;
        } catch (Exception e) {
            LOGGER.error(JSON_ERROR_FORMAT, json);
            throw new ConfigBaseException(JSON_ERROR_MESSAGE);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeRef) {
        Object rst = null;
        try {
            rst = mapper.readValue(json, typeRef);
            return (T) rst;
        } catch (Exception e) {
            LOGGER.error(JSON_ERROR_FORMAT, json);
            throw new ConfigBaseException(JSON_ERROR_MESSAGE);
        }
    }

    public static <T> T fromJsonUnderline2Hump(String json, Class<T> type) {
        Object rst = null;
        try {
            rst = underline2Hump.readValue(json, type);
            return (T) rst;
        } catch (Exception e) {
            LOGGER.error(JSON_ERROR_FORMAT, json);
            throw new ConfigBaseException(JSON_ERROR_MESSAGE);
        }
    }

    public static <T> T fromJsonUnderline2Hump(String json, TypeReference<T> typeRef) {
        Object rst = null;
        try {
            rst = underline2Hump.readValue(json, typeRef);
            return (T) rst;
        } catch (Exception e) {
            LOGGER.error(JSON_ERROR_FORMAT, json);
            throw new ConfigBaseException(JSON_ERROR_MESSAGE);
        }
    }

}
