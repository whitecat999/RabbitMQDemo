package com.example.rabbitmq.producer;

import com.example.rabbitmq.constart.Constans;
import com.example.rabbitmq.entity.BrokerMessageLog;
import com.example.rabbitmq.entity.Order;
import com.example.rabbitmq.mapper.BrokerMessageLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: rabbitmq
 * @description: ${description}
 * @author: WangZiYu
 * @create: 2019-04-01 23:48
 **/
@Component
@Slf4j
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * 消息确认回调
     */
    private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("correlationData："+correlationData);
            String messageId = correlationData.getId();
            log.info("消息确认返回值：" + ack);
            if (ack){
                //如果返回成功，则进行更新
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constans.ORDER_SEND_SUCCESS, new Date());
            }else {
                //失败进行操作：根据具体失败原因选择重试或补偿等手段
                log.error("异常处理,返回结果：" + cause);
            }

        }
    };

    public void sendOrder(Order order) {
        // 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange", // exchange
                "order.abcd", // routingKey
                order, // 消息内容
                correlationData); // correlationData 消息唯一ID
    }

    public void sendStr(String str) {
        rabbitTemplate.convertAndSend("str-exchange", // exchange
                "str.abcd", // routingKey
                str); // correlationData 消息唯一ID
    }

}
