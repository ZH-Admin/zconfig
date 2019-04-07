package com.ccsu.server.web;

import com.alibaba.fastjson.JSONObject;
import com.ccsu.common.entity.BaseRes;
import com.ccsu.common.utils.BaseResUtil;
import com.ccsu.server.service.ConfigManageService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    // 新增
    @PostMapping("/config")
    public BaseRes registerConfig(@RequestParam String appName, @RequestParam String key,
                                  @RequestBody ConcurrentHashMap config) {
        log.info("appName:{}, key:{}, config:{}", appName, key, config);
        configManageService.registerConfig(appName, key, config);
        return BaseResUtil.success();
    }

    // 修改
    @PostMapping("/modify")
    public BaseRes modifyConfig() {
        return BaseResUtil.success();
    }

    @GetMapping("/config")
    public BaseRes getConfig(@RequestParam String appName, @RequestParam String key) {
        log.info("appName:{}, key:{}", appName, key);
        Map<String, String> config = configManageService.getConfig(appName, key);
        return BaseResUtil.success(config);
    }

    @GetMapping("/configs")
    public BaseRes getConfigs(@RequestParam String appName, @RequestParam String keys) {
        Map<String, Map<String, String>> configs = configManageService
                .getConfigs(appName, Lists.newArrayList(keys.split(",")));

        return BaseResUtil.success(configs);
    }

    public static void main(String[] args) {
        ConcurrentHashMap map = new ConcurrentHashMap();
        map.put("name", "hangs.zhang");
        map.put("timeout", "100");
        String s = JSONObject.toJSONString(map);
        // {"name":"hangs.zhang","timeout":"100"}
        System.out.println(s);
    }

}
