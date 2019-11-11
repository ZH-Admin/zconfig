package com.hang.client.service;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 *  客户端config进行管理
 *  scope：作用在field上的config
 */
public interface HotConfigProcessor {

    void setConfig(String key, Map<String, String> map);

    void getConfig(String key);

    Map<String, String> getConfigFromRemote(String key);

    void updateAll();

    void updateByKey(String key);

}
