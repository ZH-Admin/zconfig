package com.github.server.service.impl;

import com.github.server.service.KafkaMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Service;

/**
 * @author hangs.zhang
 * @date 2019/4/15.
 * *****************
 * function:
 */
@Slf4j
@Service
public class KafkaMessageServiceImpl implements KafkaMessageService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaMessageServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String topic, String data) {
        log.info("向kafka推送数据:[{}]", data);
        try {
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送数据出错！！！topic:{}\tdata:{}", topic, data);
            log.error("发送数据出错=====>", e);
        }

        // 消息发送的监听器，用于回调返回信息
        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
                log.info("topic:{}\t发送成功", topic);
            }

            @Override
            public void onError(String topic, Integer partition, String key, String value, Exception exception) {
                log.error("topic:{}\t发送失败", topic);
            }
        });
    }

}
