package com.github.server.web;

import com.github.client.model.bo.ConfigRequest;
import com.github.client.model.bo.ConfigResponse;
import com.github.client.rpc.ConfigClient;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping("/config")
    public ConfigResponse getConfig(ConfigRequest configRequest) {
        return null;
    }

    @Override
    @PostMapping("/checkUpdate")
    public ConfigResponse checkUpdate(ConfigRequest configRequest) {
        ConfigResponse configResponse = new ConfigResponse();
        configResponse.setVersion(9);
        return configResponse;
    }

}
