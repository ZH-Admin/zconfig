package com.github.client.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.client.model.ConfigInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanghang
 * @date 2021/2/9 10:24 上午
 * *****************
 * function:
 */
public final class ConfigServerHttpClient {

    private static final OkHttpClient HTTP_CLIENT;

    private static final int HTTP_304 = 304;

    private static final int HTTP_200 = 200;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ConfigServerHttpClient() {
    }

    static {
        HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();
    }

    public static boolean checkConfigUpdate() {
        boolean result = true;
        String url = AppConfig.getConfigServerUrl() + "/checkConfig/" + AppConfig.getName();
        Request request = new Request.Builder().url(url).get().build();

        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            int code = response.code();
            if (HTTP_304 == code) {
                result = false;
            }
        } catch (Exception e) {
            LOGGER.error("request config server error", e);
        }

        return result;
    }

    public static List<ConfigInfo> getConfig() {
        List<ConfigInfo> result = null;
        String url = AppConfig.getConfigServerUrl() + "/config/" + AppConfig.getName();
        Request request = new Request.Builder().url(url).get().build();

        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            int code = response.code();
            if (HTTP_200 == code && response.body() != null) {
                String body = response.body().string();
                if (StringUtils.isNotBlank(body)) {
                    result = JsonUtils.fromJson(body, new TypeReference<List<ConfigInfo>>() {
                    });
                }
            }
        } catch (Exception e) {
            LOGGER.error("request config server error", e);
        }

        return result;
    }

    public static ConfigInfo getConfig(String dataId) {
        ConfigInfo result = null;
        String url = AppConfig.getConfigServerUrl() + "/config/" + AppConfig.getName() + "/" + dataId;
        Request request = new Request.Builder().url(url).get().build();

        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            int code = response.code();
            if (HTTP_200 == code && response.body() != null) {
                String body = response.body().string();
                if (StringUtils.isNotBlank(body)) {
                    result = JsonUtils.fromJson(body, ConfigInfo.class);
                }
            }
        } catch (Exception e) {
            LOGGER.error("request config server error", e);
        }

        return result;
    }

}
