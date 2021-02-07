package com.github.server.web;

import com.github.pojo.BaseResult;
import com.github.server.entity.request.ConfigEntity;
import com.github.server.service.ConfigManageService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2019/4/6
 * *****************
 * function:
 */
// @RestController
public class ConfigController {

    @Autowired
    private ConfigManageService configManageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * 新增
     */
    @PostMapping("/newConfig")
    public BaseResult<Object> registerConfig(@RequestParam String appName, @RequestParam String key,
                                             @RequestBody Map<String, String> config) {
        LOGGER.info("appName:{}, key:{}, config:{}", appName, key, config);
        configManageService.registerConfig(appName, key, config);
        return BaseResult.success();
    }

    /**
     * 修改
     */
    @PostMapping("/config")
    public BaseResult<Object> modifyConfig(@RequestBody ConfigEntity configEntity) {
        LOGGER.info("modify entity:{}", configEntity);
        configManageService.putSectionConfig(configEntity.getAppName(), configEntity.getKey(), configEntity.getConfig());
        return BaseResult.success();
    }

    @GetMapping("/config")
    public BaseResult<Map<String, String>> getConfig(@RequestParam String appName, @RequestParam String key) {
        LOGGER.info("appName:{}, key:{}", appName, key);
        return BaseResult.success(configManageService.getConfig(appName, key));
    }

    @GetMapping("/configs")
    public BaseResult<Object> getConfigs(@RequestParam String appName, @RequestParam String keys) {
        Map<String, Map<String, String>> configs = configManageService.getConfigs(appName, Lists.newArrayList(keys.split(",")));
        return BaseResult.success(configs);
    }

}
