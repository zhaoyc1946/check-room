package com.check.server.utils;

import com.check.commom.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @description: redis操作工具类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 17:24:36
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, String> hashOperations;
    @Autowired
    private ListOperations<String, String> listOperations;
    @Autowired
    private SetOperations<String, String> setOperations;
    @Autowired
    private ZSetOperations<String, String> zSetOperations;

    /**
     * 默认过期时长，单位：秒
     */
    public final long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 不设置过期时长
     */
    public final long NOT_EXPIRE = -1;

    /**
     * 向redis中存放数据
     *
     * @param key    键
     * @param value  值
     * @param expire 过期时间
     */
    public void set(String key, Object value, long expire) {
        valueOperations.set(key, JsonUtils.objectToJson(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 从redis中取出指定键对应的值
     *
     * @param key 键
     * @return
     */
    public String get(String key) {
        return valueOperations.get(key);
    }

    /**
     * hash的添加操作
     *
     * @param tabKey hash键
     * @param rowId  hash字段
     * @param value  值
     */
    public void hset(String tabKey, String rowId, String value) {
        hashOperations.put(tabKey, rowId, value);
    }


    /**
     * hash 获取操作
     *
     * @param tabKey hash键
     * @param rowId  hash字段
     * @return 值
     */
    public String hget(String tabKey, String rowId) {
        return hashOperations.get(tabKey, rowId);
    }

    /**
     * hash 删除操作
     *
     * @param tabKey hash键
     * @param rowId  hash字段
     * @return
     */
    public Long hdel(String tabKey, String... rowId) {
        return hashOperations.delete(tabKey, rowId);
    }
}
