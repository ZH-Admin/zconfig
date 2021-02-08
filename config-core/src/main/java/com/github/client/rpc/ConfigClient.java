package com.github.client.rpc;

import com.github.client.model.bo.ConfigRequest;
import com.github.client.model.bo.ConfigResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hangs.zhang
 * @date 2020/07/18 23:49
 * *****************
 * function:
 */
@FeignClient(name = "CONFIG-SERVER", url = "${config-server}")
public interface ConfigClient {

    @PostMapping("/config")
    ConfigResponse getConfig(@RequestBody ConfigRequest configRequest);

    @PostMapping("/checkUpdate")
    ConfigResponse checkUpdate(@RequestBody ConfigRequest configRequest);

}