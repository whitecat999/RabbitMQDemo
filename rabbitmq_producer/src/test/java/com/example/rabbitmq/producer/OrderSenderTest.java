package com.example.rabbitmq.producer;

import com.example.rabbitmq.entity.Order;
import com.example.rabbitmq.utils.FastJsonConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @program: rabbitmq
 * @description: ${description}
 * @author: WangZiYu
 * @create: 2019-04-02 00:13
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderSenderTest {

    @Autowired
    private OrderSender orderSender;

    @Test
    public void sendOrder() throws Exception{
        Order order = new Order();
        order.setId("20190401000000001");
        order.setName("测试订单1");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        orderSender.sendOrder(order);
    }


    @Test
    public void sendStr() throws Exception{
        orderSender.sendStr("haha");
    }

    @Test
    public void testJsonUtil () {
        Order order = new Order();
        order.setId("123321");
        order.setMessageId("123123123");
        order.setName("123123");
        String jsonStr = FastJsonConvertUtil.convertObjectToJSON(order);
        Order object = FastJsonConvertUtil.convertJSONToObject(jsonStr);
        System.out.println(object.toString());
    }
}