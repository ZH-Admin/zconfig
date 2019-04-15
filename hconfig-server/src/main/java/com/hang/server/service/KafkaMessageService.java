package com.hang.server.service;

/**
 * @author hangs.zhang
 * @date 2019/4/15.
 * *****************
 * function:
 */
public interface KafkaMessageService {

    void sendMessage(String topic, String data);

}
