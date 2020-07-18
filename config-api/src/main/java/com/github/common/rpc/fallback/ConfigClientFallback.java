package com.github.common.rpc.fallback;

import com.github.common.entity.bo.ConfigInfo;
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
    public ConfigInfo getConfig(ConfigInfo request) {
        return null;
    }

}
