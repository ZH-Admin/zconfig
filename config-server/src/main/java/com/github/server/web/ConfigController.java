package com.github.server.web;

import com.hang.common.entity.BaseResult;
import com.hang.common.entity.bo.ConfigInfo;
import com.github.server.entity.request.ConfigEntity;
import com.github.server.service.ConfigManageService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2019/4/6
 * *****************
 * function:
 */
@Slf4j
@RestController
public class ConfigController {

    @Autowired
    private ConfigManageService configManageService;

    /**
     * 新增
     */
    @PostMapping("/newConfig")
    public BaseResult registerConfig(@RequestParam String appName, @RequestParam String key,
                                     @RequestBody Map config) {
        log.info("appName:{}, key:{}, config:{}", appName, key, config);
        configManageService.registerConfig(appName, key, config);
        return BaseResult.success();
    }

    /**
     * 修改
     */
    @PostMapping("/config")
    public BaseResult modifyConfig(@RequestBody ConfigEntity configEntity) {
        log.info("modify entity:{}", configEntity);
        configManageService.putSectionConfig(configEntity.getAppName(), configEntity.getKey(), configEntity.getConfig());
        return BaseResult.success();
    }

    @GetMapping("/config")
    public BaseResult<ConfigInfo> getConfig(@RequestParam String appName, @RequestParam String key) {
        log.info("appName:{}, key:{}", appName, key);
        Map<String, String> config = configManageService.getConfig(appName, key);
        return BaseResult.success(config);
    }

    @GetMapping("/configs")
    public BaseResult getConfigs(@RequestParam String appName, @RequestParam String keys) {
        Map<String, Map<String, String>> configs = configManageService
                .getConfigs(appName, Lists.newArrayList(keys.split(",")));

        return BaseResult.success(configs);
    }

}
