package com.ccsu.server.config;

import com.ccsu.server.ServerApplication;
import com.ccsu.server.handler.interceptors.AccessInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author ZhangHang
 * @date 18-7-1
 * *****************
 * function:
 * 继承了该类之后 所有web配置自动失效
 */
@Configuration
@ComponentScan(basePackageClasses = ServerApplication.class, useDefaultFilters = true)
public class WebConfig extends WebMvcConfigurationSupport {

    /**
     * 配置web静态资源位置
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessInterceptor())
                // 拦截
                .addPathPatterns("/**")
                // 不拦截
                .excludePathPatterns("/no");
    }


}
