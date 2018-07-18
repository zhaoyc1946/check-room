package com.check.server.utils;

import com.check.commom.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
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
     * 删除指定的key
     *
     * @param key 键
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除redis指定key的集合
     *
     * @param keys key集合
     */
    public void del(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 根据指定的模板获取redis key集合
     *
     * @param pattern 模板
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
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

    /**
     * 在redis中做原子加或者减操作
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @return 原子加之后的结果
     */
    public int atomAddOrMinus(final String tabKey, final Integer rowId, final Integer value) {

        // 如果是减操作, 保证数据不为负数
        if (value < 1) {
            final Object strValue = hashOperations.get(tabKey, rowId.toString());

            if (Integer.valueOf(strValue.toString()) < 1) {
                return 0;
            }
        }

        final Long result = hashOperations.increment(tabKey, rowId.toString(), value);

        return result.intValue();
    }

    /**
     * Zset添加操作
     *
     * @param key   键
     * @param value 值
     * @param score 排序值
     * @return
     */
    public Boolean zadd(String key, String value, double score) {
        return zSetOperations.add(key, value, score);
    }

    /**
     * Zset操作, 删除一个或者多个元素，返回删除元素的个数
     *
     * @param key   键
     * @param value 要删除的元素的值
     * @return
     */
    public Long zrem(String key, String... value) {
        return zSetOperations.remove(key, value);
    }

    /**
     * Zset操作, 删除指定区间的值
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public Long zremrangebyLex(String key, Integer min, Integer max) {
        return zSetOperations.removeRangeByScore(key, min, max);
    }

    /**
     * Zset操作, 按照索引从小到大的顺序返回指定索引start到stop之间的元素,参数WITHSCORES指定显示分数
     *
     * @param key   键
     * @param start 查询起始值
     * @param end   查询结束值
     * @return
     */
    public Set<String> zrange(String key, long start, long end) {
        return zSetOperations.range(key, start, end);
    }

    /**
     * Zset获得集合中元素的数量
     *
     * @param key 键
     * @return
     */
    public Long zcard(String key) {
        return zSetOperations.size(key);
    }

    /**
     * zset操作, 根据元素值返回元素对应的索引位置
     *
     * @param key   键
     * @param value 值
     * @return 索引下标
     */
    public Long zrank(String key, String value) {
        return zSetOperations.rank(key, value);
    }

    /**
     * zset操作, 返回有序集合中分值介于min和max之间的所有成员，包括min和max在内，并按照分值从小到大的排序来返回他们
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public Set<String> zrangebyscore(String key, double min, double max) {
        return zSetOperations.rangeByScore(key, min, max);
    }

}
