package com.github.server.discovery;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhanghang
 * @date 2021/2/9 5:18 下午
 * *****************
 * function:
 */
@Component
public class RDBDiscovery {

    public List<ConfigServerInstance> getAllInsatnce() {
        return Lists.newArrayList();
    }

    public void register() {

    }

    public void offline() {

    }

}
