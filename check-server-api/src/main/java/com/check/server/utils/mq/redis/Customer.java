package com.check.server.utils.mq.redis;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListener;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.check.commom.mq.MQTopic;
import com.check.commom.utils.JsonUtils;
import com.check.server.config.RocketMQConfig;
import com.check.server.utils.RedisUtils;
import com.check.server.utils.mq.redis.bean.RedisDeleteKeyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 删除redis消费者
 * @author: Mr.ZHAO
 * @cereate: 2018/07/16 21:21:34
 */
@Component
public class Customer implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void run(String... args) throws Exception {
        // 删除redis key
        delRedisKey();
        // 删除redis zset key
        delRedisZsetKey();
        // 删除redis hash key
        delRedisHashKey();
    }

    /**
     * 删除redis key
     */
    private void delRedisKey() {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MQTopic.top_redisKey.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MQTopic.top_redisKey.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动, 从消息队列头部读取数据
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    logger.debug("customer delete redis key consume, count = {}", list.size());
                    // redis key集合
                    Set<String> set = new LinkedHashSet<>(0);

                    for (MessageExt message : list) {
                        set.add(new String(message.getBody()));
                    }

                    if (set.size() > 0) {
                        redisUtils.del(set);
                    }
                    set = null;

                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            logger.error("delete redis key exception, message: ", e);
        }
    }

    /**
     * 删除redis zset key
     */
    private void delRedisZsetKey() {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MQTopic.top_redisZsetKey.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MQTopic.top_redisZsetKey.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动, 从消息队列头部读取数据
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    logger.debug("customer delete redis zset key consume, count = {}", list.size());

                    RedisDeleteKeyEntity key = null;

                    for (MessageExt message : list) {
                        String value = new String(message.getBody());
                        key = JsonUtils.jsonToPojo(value, RedisDeleteKeyEntity.class);

                        redisUtils.zrem(key.getKey(), key.getField());
                    }
                    key = null;

                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            logger.error("delete redis zset key exception, message: ", e);
        }
    }

    /**
     * 删除redis hash key
     */
    private void delRedisHashKey() {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MQTopic.top_redisHashKey.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MQTopic.top_redisHashKey.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动, 从消息队列头部读取数据
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    logger.debug("customer delete redis hash key consume, count = {}", list.size());

                    RedisDeleteKeyEntity key = null;

                    for (MessageExt message : list) {
                        String value = new String(message.getBody());
                        key = JsonUtils.jsonToPojo(value, RedisDeleteKeyEntity.class);

                        redisUtils.hdel(key.getKey(), key.getField());
                    }
                    key = null;

                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            logger.error("delete redis hash key exception, message: ", e);
        }
    }
}
