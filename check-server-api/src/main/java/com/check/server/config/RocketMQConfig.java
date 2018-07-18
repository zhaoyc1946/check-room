package com.check.server.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @description: MQ初始化配置配置文件
 * @author: Mr.ZHAO
 * @cereate: 2018/07/12 23:35:49
 */
@Configuration
public class RocketMQConfig {

    private static final Logger logger = LoggerFactory.getLogger(RocketMQConfig.class);

    /**
     * 消息生产者
     */
    private static DefaultMQProducer SENDER;

    /**
     * mq地址
     */
    private static String NAME_SERVER;

    @Value("${rocketmq.redis.namesrvAddr}")
    public void setNameServer(String nameServer) {
        NAME_SERVER = nameServer;
    }

    /**
     * 发送消息超时时间
     */
    private static Integer sendMessageTimeOut;

    @Value("${rocketmq.redis.sendMsgTimeout}")
    public void setSendMsgTimeout(Integer sendMsgTimeout) {
        sendMessageTimeOut = sendMsgTimeout;
    }

    /**
     * 生产者组名
     */
    private static final String PRODUCER_GROUP_NAME = "checkServerMQ";

    /**
     * 消费者组名
     */
    private static final String CONSUMER_GROUP_NAME = "checkServerMQ";

    /**
     * 生成删生产者
     *
     * @return
     */
    public static DefaultMQProducer init() {
        if (SENDER != null) return SENDER;

        synchronized (RocketMQConfig.class) {
            if (SENDER != null) return SENDER;

            logger.info("Product init name server: {}", NAME_SERVER);

            SENDER = new DefaultMQProducer(PRODUCER_GROUP_NAME);
            SENDER.setNamesrvAddr(NAME_SERVER);
            SENDER.setInstanceName(UUID.randomUUID().toString());

            try {
                SENDER.start();
            } catch (Exception e) {
                logger.error("Product init field, message: ", e);
            }
            SENDER.setSendMsgTimeout(sendMessageTimeOut);
            return SENDER;
        }
    }

    /**
     * 生成消费者
     *
     * @return
     */
    public static DefaultMQPushConsumer initConsumer() {
        return initConsumer(CONSUMER_GROUP_NAME);
    }

    /**
     * 生成消费者
     *
     * @return
     */
    public static DefaultMQPushConsumer initConsumer(String consumerGroup) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(NAME_SERVER);
        consumer.setConsumeConcurrentlyMaxSpan(10);
        logger.debug("MQ consumer name_server: {} create consumer group : {}", NAME_SERVER, consumerGroup);
        return consumer;
    }
}
