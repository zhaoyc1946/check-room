package com.check.commom.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: redis分布式锁工具类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 18:08:40
 */
public class RedisLockUtil {

    /**
     * redis锁超时时间, 30秒钟
     */
    private final long LOCK_TIMEOUT_IN_SECOND = 30;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 尝试在给定的keys上加锁, 值为uuid的字符串
     *
     * @param keys 需要枷锁的key列表
     * @return true代表加锁成功且超时时间都设置成功, false为失败
     */
    public boolean lock(final UUID uuid, final String ...keys) {
        if (keys == null || keys.length == 0) {
            return false;
        }

        if (uuid == null) {
            return false;
        }

        for (String key : keys) {
            final Boolean lock = redisTemplate.opsForValue().setIfAbsent(key, uuid.toString());
            if (!lock) {
                return false;
            }
            final Boolean expire = redisTemplate.expire(key, LOCK_TIMEOUT_IN_SECOND, TimeUnit.SECONDS);
            if (!expire) {
                redisTemplate.delete(key);
                return false;
            }
        }

        return true;
    }

    /**
     * 尝试解锁redis中值为uuid的keys
     *
     * 在finally代码块中执行这个方法
     *
     * @param uuid
     * @param keys
     */
    public void unlock(final UUID uuid, final String ...keys) {
        if (keys == null || keys.length == 0) {
            return;
        }

        if (uuid == null) {
            return;
        }

        for (String key : keys) {
            if (!redisTemplate.hasKey(key)) {
                continue;
            }
            final Object val = redisTemplate.opsForValue().get(key);
            if (val == null) {
                continue;
            }

            if (uuid.toString().equals(val)) {
                redisTemplate.delete(key);
            }
        }
    }
}
