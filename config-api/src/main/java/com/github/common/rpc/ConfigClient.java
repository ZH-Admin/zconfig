package com.github.common.rpc;

import com.github.common.pojo.bo.ConfigRequest;
import com.github.common.pojo.bo.ConfigResponse;
import com.github.common.rpc.fallback.ConfigClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hangs.zhang
 * @date 2020/07/18 23:49
 * *****************
 * function:
 */
@FeignClient(name = "HCONFIG-SERVER-SERVICE", fallback = ConfigClientFallback.class)
public interface ConfigClient {

    @GetMapping("/config")
    ConfigResponse getConfig(ConfigRequest configRequest);

    @GetMapping("/checkUpdate")
    boolean checkUpdate(ConfigRequest configRequest);

}