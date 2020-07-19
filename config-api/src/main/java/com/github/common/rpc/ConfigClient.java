package com.github.common.rpc;

import com.github.common.entity.bo.ConfigInfo;
import com.github.common.rpc.fallback.ConfigClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2020/07/18 23:49
 * *****************
 * function:
 */
@FeignClient(name = "HCONFIG-SERVER-SERVICE", fallback = ConfigClientFallback.class)
public interface ConfigClient {

    @GetMapping("/config")
    Map<String, String> getConfig(ConfigInfo request);

    @GetMapping("/checkUpdate")
    boolean checkUpdate(ConfigInfo configInfo);

}
