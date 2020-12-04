package com.github.common.rpc.fallback;

import com.github.common.pojo.bo.ConfigRequest;
import com.github.common.pojo.bo.ConfigResponse;
import com.github.common.rpc.ConfigClient;
import org.springframework.stereotype.Component;


/**
 * @author hangs.zhang
 * @date 2020/07/19 00:03
 * *****************
 * function:
 */
@Component
public class ConfigClientFallback implements ConfigClient {

    @Override
    public ConfigResponse getConfig(ConfigRequest configRequest) {
        return null;
    }

    @Override
    public boolean checkUpdate(ConfigRequest configRequest) {
        return false;
    }
}
