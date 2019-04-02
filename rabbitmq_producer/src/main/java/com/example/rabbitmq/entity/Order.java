package com.example.rabbitmq.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: rabbitmq
 * @description: 订单类
 * @author: WangZiYu
 * @create: 2019-04-01 23:37
 **/
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 4659812782998325176L;

    /**
     * 订单id
     */
    private String id;

    /**
     * 订单名称
     */
    private String name;

    /**
     * 消息唯一标识
     */
    private String messageId;

}
