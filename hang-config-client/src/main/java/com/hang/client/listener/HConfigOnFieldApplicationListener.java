package com.hang.client.listener;

import com.hang.client.service.HotConfigOnFieldManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * @author hangs.zhang
 * @date 2018/8/13
 * *********************
 * function:
 * ApplicationListener：
 *  需要处理一些操作，比如一些数据的加载、初始化缓存、特定任务的注册等等。
 *  这个时候我们就可以使用Spring提供的ApplicationListener来进行操作
 *
 *
 */
@Slf4j
@Component
public class HConfigOnFieldApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private HotConfigOnFieldManager hotConfigManager;

    @Autowired
    public HConfigOnFieldApplicationListener(HotConfigOnFieldManager hotConfigManager) {
        this.hotConfigManager = hotConfigManager;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            log.info("=====ContextRefreshedEvent====={}", contextRefreshedEvent.getSource().getClass().getName());
        }
    }
}
