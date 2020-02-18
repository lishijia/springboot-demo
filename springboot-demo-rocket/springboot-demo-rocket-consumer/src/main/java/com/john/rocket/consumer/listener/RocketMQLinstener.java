package com.john.rocket.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class RocketMQLinstener implements MessageListenerConcurrently{

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(
            List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        String logPre = "[RocketMQ 消费监听]";
        log.info("{} 接收消息", logPre);
        if (CollectionUtils.isEmpty(list)){
            log.info("{} 接收消息 list size = 0", logPre);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        log.info("{} 接收消息 message = {}", logPre, messageExt);
        log.info("{} 接受到的消息为 body = {}", logPre, new String(messageExt.getBody()));
        // 消息消费成功
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
