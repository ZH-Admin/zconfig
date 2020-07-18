package com.github.server.web;

import com.github.server.service.KafkaMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hangs.zhang
 * @date 2019/4/15.
 * *****************
 * function:
 */
@RestController
public class MessageController {

    @Autowired
    private KafkaMessageService kafkaMessageService;

    @GetMapping("/send")
    public String send(String topic, String data) {
        kafkaMessageService.sendMessage(topic, data);
        return "send success";
    }

}
