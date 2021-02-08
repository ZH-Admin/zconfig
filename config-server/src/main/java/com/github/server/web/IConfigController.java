package com.github.server.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.client.enums.ConfigType;
import com.github.client.model.bo.ConfigRequest;
import com.github.client.model.bo.ConfigResponse;
import com.github.client.rpc.ConfigClient;
import com.github.client.utils.JsonUtils;
import com.github.server.entity.po.PropertiesPO;
import com.github.server.service.ConfigManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private ConfigManageService configManageService;

    @Override
    @PostMapping("/config")
    public ConfigResponse getConfig(@RequestBody ConfigRequest configRequest) {
        PropertiesPO propertiesPO = configManageService.getConfig(configRequest.getAppName(), configRequest.getDataId());
        ConfigResponse configResponse = new ConfigResponse();
        configResponse.setVersion(propertiesPO.getVersion());
        configResponse.setContent(JsonUtils.fromJson(propertiesPO.getContent(), new TypeReference<Map<String, String>>() {}));
        configResponse.setConfigType(ConfigType.PROPERTIES);
        return configResponse;
    }

    @Override
    @PostMapping("/checkUpdate")
    public ConfigResponse checkUpdate(@RequestBody ConfigRequest configRequest) {
        PropertiesPO propertiesPO = configManageService.getConfig(configRequest.getAppName(), configRequest.getDataId());
        ConfigResponse configResponse = new ConfigResponse();
        configResponse.setVersion(propertiesPO.getVersion());
        return configResponse;
    }

}
