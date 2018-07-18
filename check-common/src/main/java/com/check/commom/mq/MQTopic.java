package com.check.commom.mq;

/**
 * @description: MQ主题
 * @author: Mr.ZHAO
 * @cereate: 2018/07/14 22:29:28
 */
public enum MQTopic {
    /**
     * 普通redis key
     */
    top_redisKey,
    /**
     * redis hash key
     */
    top_redisHashKey,
    /**
     * redis zset key
     */
    top_redisZsetKey;
}
