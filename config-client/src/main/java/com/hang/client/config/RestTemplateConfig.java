package com.hang.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author hangs.zhang
 * @date 2019/4/7
 * *****************
 * function:
 */
@Configuration
public class RestTemplateConfig {

    @Bean("configRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
