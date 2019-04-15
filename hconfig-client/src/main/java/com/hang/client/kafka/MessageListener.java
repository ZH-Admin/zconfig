package com.hang.client.kafka;

import com.hang.client.service.HotConfigOnFieldManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author hangs.zhang
 * @date 2019/4/15.
 * *****************
 * function:
 */
@Slf4j
@Component
public class MessageListener {

    @Autowired
    private HotConfigOnFieldManager configOnFieldManager;

    @KafkaListener(topics = {"${my-app.name}"}, groupId = "group-config")
    public void processMessage(ConsumerRecord<Integer, String> record) {
        log.info("processMessage, topic = {}, msg = {}", record.topic(), record.value());
        configOnFieldManager.updateAll();
    }

}
