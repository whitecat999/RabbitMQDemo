package com.example.rabbitmq.service;

import com.example.rabbitmq.constart.Constans;
import com.example.rabbitmq.entity.BrokerMessageLog;
import com.example.rabbitmq.entity.Order;
import com.example.rabbitmq.mapper.BrokerMessageLogMapper;
import com.example.rabbitmq.mapper.OrderMapper;
import com.example.rabbitmq.producer.OrderSender;
import com.example.rabbitmq.utils.DateUtils;
import com.example.rabbitmq.utils.FastJsonConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Title: OrderService
 * @Description: 业务实现
 * @date 2019/1/2215:41
 */
@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private OrderSender orderSender;

    public void createOrder(Order order)  {
        try {
            // 使用当前时间当做订单创建时间（为了模拟一下简化）
            Date orderTime = new Date();
            // 插入业务数据
            orderMapper.insert(order);
            // 插入消息记录表数据
            BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
            // 消息唯一ID
            brokerMessageLog.setMessageId(order.getMessageId());
            // 保存消息整体 转为JSON 格式存储入库
            brokerMessageLog.setMessage(FastJsonConvertUtil.convertObjectToJSON(order));
            // 设置消息状态为0 表示发送中
            brokerMessageLog.setStatus("0");
            // 设置消息未确认超时时间窗口为 一分钟
            brokerMessageLog.setNextRetry(DateUtils.addMinutes(orderTime, Constans.ORDER_TIMEOUT));
            brokerMessageLog.setCreateTime(new Date());
            brokerMessageLog.setUpdateTime(new Date());
            brokerMessageLogMapper.insertSelective(brokerMessageLog);
            // 发送消息
            orderSender.sendOrder(order);
        } catch (Exception e) {
            log.error("订单业务异常{}",e);
        }
    }
}
