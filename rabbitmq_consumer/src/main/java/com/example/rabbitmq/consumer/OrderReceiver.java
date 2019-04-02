package com.example.rabbitmq.consumer;

import com.example.rabbitmq.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @program: rabbitmq
 * @description: ${description}
 * @author: WangZiYu
 * @create: 2019-04-02 00:33
 **/
@Component
public class OrderReceiver {

    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "order-queue", durable = "true"), // 绑定Queue名为order-queue, 持久化true
                    exchange = @Exchange(name = "order-exchange", durable = "true", type = "topic"), // 榜单Exchange名为order-exchange, 持久化true, 类型:发布订阅模式
                    key = "order.#" // Routing key 为order.开头, 匹配所有字符
            )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload Order order, // 消息体内容
                               @Headers Map<String, Object> headers, // 消息头内容
                               Channel channel) { // 手工签收用Channel
        System.out.println("---------收到消息, 开始消费---------");
        System.out.println("订单ID: " + order.getId());
        // 取出deliveryTag
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // ACK 确认签收
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "str-queue", durable = "true"),
            exchange = @Exchange(name = "str-exchange", durable = "true", type = "topic"),
            key = "str.*"
            )
    )
    public void onOrderMessage(String str,
                               @Headers Map<String, Object> headers,
                               Channel channel) { // 手工签收用Channel
        System.out.println("---------收到消息, 开始消费---------");
        System.out.println("收到: " + str);
        // 取出deliveryTag
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // ACK 确认签收
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
