package com.github.common.rpc.fallback;

import com.github.common.pojo.bo.ConfigInfo;
import com.github.common.rpc.ConfigClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2020/07/19 00:03
 * *****************
 * function:
 */
@Component
public class ConfigClientFallback implements ConfigClient {


    @Override
    public Map<String, String> getConfig(ConfigInfo request) {
        return null;
    }

    @Override
    public boolean checkUpdate(ConfigInfo configInfo) {
        return false;
    }

}
