package com.github.server.web;

import com.github.common.entity.bo.ConfigInfo;
import com.github.common.rpc.ConfigClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2020/07/19 11:46
 * *****************
 * function:
 */
@RestController
public class IConfigController implements ConfigClient {

    @Override
    @GetMapping("/config")
    public Map<String, String> getConfig(ConfigInfo request) {
        return null;
    }

    @Override
    @GetMapping("/checkUpdate")
    public boolean checkUpdate(ConfigInfo configInfo) {
        return false;
    }

}
