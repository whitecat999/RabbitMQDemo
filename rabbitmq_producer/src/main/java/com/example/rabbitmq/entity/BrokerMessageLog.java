package com.example.rabbitmq.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Title: BrokerMessageLog
 * @Description: 消息记录
 * @date 2019/1/2214:29
 */
@Data
public class BrokerMessageLog {

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 重发次数
     */
    private Integer tryCount;

    /**
     * 消息状态
     */
    private String status;

    /**
     * 超时时间
     */
    private Date nextRetry;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
