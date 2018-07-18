package com.check.server.utils.mq.redis.bean;

/**
 * @description: 删除redis保存的信息key
 * @author: Mr.ZHAO
 * @cereate: 2018/07/14 22:42:15
 */
public class RedisDeleteKeyEntity {

    private static final long serialVersionUID = 1L;

    /**
     * redis key
     */
    private String key;
    /**
     * redis 字段
     */
    private String field;

    public RedisDeleteKeyEntity(String key, String field) {
        this.key = key;
        this.field = field;
    }

    public RedisDeleteKeyEntity() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "RedisDeleteKeyEntity{" +
                "key='" + key + '\'' +
                ", field='" + field + '\'' +
                '}';
    }
}
