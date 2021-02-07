package com.github.client.spring.load;

import com.github.client.utils.AppConfig;
import com.github.pojo.bo.ConfigRequest;
import com.github.pojo.bo.ConfigResponse;
import com.github.rpc.ConfigClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author hangs.zhang
 * @date 2020/07/19 21:55
 * *****************
 * function:
 */
@Component
public class RemoteConfigProcessor {

    @Resource
    private ConfigClient configClient;

    public boolean checkUpdate(String dataId, Integer version) {
        return configClient.checkUpdate(generateConfigRequest(dataId, version));
    }

    public ConfigResponse getConfig(String dataId) {
        return configClient.getConfig(generateConfigRequest(dataId, null));
    }

    private ConfigRequest generateConfigRequest(String dataId, Integer version) {
        ConfigRequest configRequest = new ConfigRequest();
        configRequest.setAppName(AppConfig.getName());
        configRequest.setAppToken(AppConfig.getToken());
        configRequest.setDataId(dataId);
        configRequest.setVersion(version);
        return configRequest;
    }

}
