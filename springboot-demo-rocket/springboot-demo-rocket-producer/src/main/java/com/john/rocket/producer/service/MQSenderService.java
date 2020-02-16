package com.john.rocket.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQSenderService {

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    public void sendMessage(){
        log.info("准备发送消息to rocketMQ");
        Message message = new Message("JohnTopic", "JohnTag", "data".getBytes());
        try {
            defaultMQProducer.send(message);
        }catch (Exception e){
            log.error("消息发送异常 error={}", e);
        }
        log.info("消息发送完成 rocketMQ");
    }


}
