package com.example.rabbitmq.mapper;

import com.example.rabbitmq.entity.BrokerMessageLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Title: BrokerMessageLogMapper
 * @Description: 消息记录接口
 * @date 2019/1/2214:45
 */
@Mapper
public interface BrokerMessageLogMapper {
    /**
     * 查询消息状态为0(发送中) 且已经超时的消息集合
     * @return
     */
    List<BrokerMessageLog> query4StatusAndTimeoutMessage();

    /**
     * 重新发送统计count发送次数 +1
     * @param messageId
     * @param updateTime
     */
    void update4ReSend(@Param("messageId") String messageId, @Param("updateTime") Date updateTime);
    /**
     * 更新最终消息发送结果 成功 or 失败
     * @param messageId
     * @param status
     * @param updateTime
     */
    void changeBrokerMessageLogStatus(@Param("messageId") String messageId, @Param("status") String status, @Param("updateTime") Date updateTime);

    int insertSelective(BrokerMessageLog record);
}
