package com.github.common.rpc;

import com.github.common.pojo.bo.ConfigRequest;
import com.github.common.pojo.bo.ConfigResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hangs.zhang
 * @date 2020/07/18 23:49
 * *****************
 * function:
 */
@FeignClient(name = "CONFIG-SERVER", url = "http://127.0.0.1:8085")
public interface ConfigClient {

    @GetMapping("/config")
    ConfigResponse getConfig(ConfigRequest configRequest);

    @GetMapping("/checkUpdate")
    boolean checkUpdate(ConfigRequest configRequest);

}