package com.john.rocket.consumer.config;

import com.john.rocket.consumer.listener.RocketMQLinstener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@Slf4j
public class RocketMQConsumerConfig {

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.topics}")
    private String topics;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;
    @Resource
    private RocketMQLinstener registerMessageListener;
    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer(){
        log.info("准备初始化RocketMQ Consumer groupName={}, namesrvAddr={}", groupName, namesrvAddr);
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.registerMessageListener(registerMessageListener);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        try {
            consumer.subscribe("JohnTopic","*");
            consumer.start();
        }catch (MQClientException e){
            log.error("初始化RocketMQ Consumer 失败 error = {}", e);
        }
        log.info("初始化RocketMQ Consumer 成功 groupName={}, namesrvAddr={}", groupName, namesrvAddr);
        return consumer;
    }

}
