package com.check.server.utils.mq.redis;

import com.check.commom.mq.MQTopic;
import com.check.commom.utils.JsonUtils;
import com.check.server.utils.mq.ProductMQ;
import com.check.server.utils.mq.redis.bean.RedisDeleteKeyEntity;

/**
 * @description: 删除redis中保存的信息生产者
 * @author: Mr.ZHAO
 * @cereate: 2018/07/14 22:35:00
 */
public class RedisDeleteKeyProduct {

    /**
     * 删除redis中保存的key值信息
     *
     * @param key
     */
    public static void deleteKey(String key) {
        ProductMQ.sendMessage(MQTopic.top_redisKey, key);
    }

    /**
     * 删除redis中保存的hash key
     *
     * @param key   键
     * @param field 字段
     */
    public static void deleteHashKey(String key, String field) {
        String message = JsonUtils.objectToJson(new RedisDeleteKeyEntity(key, field));
        ProductMQ.sendMessage(MQTopic.top_redisHashKey, message);
    }

    /**
     * 删除redis中保存的zset操作, 根据值删除键
     *
     * @param key   键
     * @param value 值
     */
    public static void deleteZsetRedis(String key, String value) {
        String message = JsonUtils.objectToJson(new RedisDeleteKeyEntity(key, value));
        ProductMQ.sendMessage(MQTopic.top_redisZsetKey, message);
    }
}
