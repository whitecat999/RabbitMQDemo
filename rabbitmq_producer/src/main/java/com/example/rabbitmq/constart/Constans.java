package com.example.rabbitmq.constart;

/**
 * @program: rabbitmq_producer
 * @description: ${description}
 * @author: WangZiYu
 * @create: 2019-04-02 20:44
 **/
public class Constans {
    /**
     * 超时时长,单位min
     */
    public static final Integer ORDER_TIMEOUT = 1;
    /**
     * 发送中
     */
    public static final String ORDER_SENDING = "0";

    /**
     * 发送成功
     */
    public static final String ORDER_SEND_SUCCESS = "1";

    /**
     * 发送失败
     */
    public static final String ORDER_SEND_FAILURE = "2";
}
