package com.github.server.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.client.enums.ConfigType;
import com.github.client.model.BaseResult;
import com.github.client.model.ConfigInfo;
import com.github.client.utils.JsonUtils;
import com.github.client.utils.KeyUtils;
import com.github.server.entity.po.PropertiesPO;
import com.github.server.service.ConfigService;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hangs.zhang
 * @date 2020/07/19 11:46
 * *****************
 * function:
 */
@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;

    private static final Multimap<String, DeferredResult<String>> MULTIMAP
            = Multimaps.synchronizedMultimap(HashMultimap.create());

    private static final String UPDATE = "update";

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public void handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e, HttpServletRequest request) {
        LOGGER.info("config has no change");
    }

    @GetMapping("/config/{appName}")
    public List<ConfigInfo> getConfig(@PathVariable("appName") String appName) {
        List<PropertiesPO> configs = configService.getConfig(appName);
        if (CollectionUtils.isEmpty(configs)) return Lists.newArrayList();
        return configs.stream().map(this::convert2ConfigResponse).collect(Collectors.toList());
    }

    @GetMapping("/config/{appName}/{dataId}")
    public ConfigInfo getConfig(@PathVariable("appName") String appName, @PathVariable("dataId") String dataId) {
        PropertiesPO propertiesPO = configService.getConfig(appName, dataId);
        if (propertiesPO == null) return null;
        return convert2ConfigResponse(propertiesPO);
    }

    @GetMapping("/checkConfig/{appName}")
    public DeferredResult<String> checkConfigUpdate(@PathVariable("appName") String appName) {
        DeferredResult<String> result = new DeferredResult<>();
        MULTIMAP.put(appName, result);
        return result;
    }

    @GetMapping("/checkConfig/{appName}/{version}")
    public DeferredResult<String> checkConfigUpdate(@PathVariable("appName") String appName,
                                                    @PathVariable(name = "version", required = false) Integer version) {
        DeferredResult<String> result = new DeferredResult<>();
        Integer maxAppVersion = configService.getMaxAppVersion(appName);
        if (version != null && maxAppVersion != null && version < configService.getMaxAppVersion(appName)) {
            result.setResult(UPDATE);
        } else {
            MULTIMAP.put(appName, result);
        }
        return result;
    }

    @GetMapping("/setResult/{appName}/{dataId}")
    public BaseResult<Void> setResult(@PathVariable("appName") String appName, @PathVariable("dataId") String dataId) {
        Collection<DeferredResult<String>> deferredResults = MULTIMAP.removeAll(KeyUtils.getKey(appName, dataId));
        for (DeferredResult<String> deferredResult : deferredResults) {
            // 判断客户端是否已经断开连接
            if (!deferredResult.isSetOrExpired()) {
                deferredResult.setResult(UPDATE);
            }
        }
        return BaseResult.success();
    }

    private ConfigInfo convert2ConfigResponse(PropertiesPO propertiesPO) {
        ConfigInfo configInfo = new ConfigInfo();
        BeanUtils.copyProperties(propertiesPO, configInfo);
        configInfo.setContent(JsonUtils.fromJson(propertiesPO.getContent(), new TypeReference<Map<String, String>>() {
        }));
        configInfo.setConfigType(ConfigType.PROPERTIES);
        return configInfo;
    }

}
