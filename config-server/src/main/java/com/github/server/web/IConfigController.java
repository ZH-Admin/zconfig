package com.github.server.web;

import com.github.pojo.bo.ConfigRequest;
import com.github.pojo.bo.ConfigResponse;
import com.github.rpc.ConfigClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ConfigResponse getConfig(ConfigRequest configRequest) {
        return null;
    }

    @Override
    @GetMapping("/checkUpdate")
    public boolean checkUpdate(ConfigRequest configRequest) {
        return false;
    }

}
