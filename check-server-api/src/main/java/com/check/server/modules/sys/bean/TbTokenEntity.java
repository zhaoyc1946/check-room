package com.check.server.modules.sys.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: token对应实体类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 22:27:16
 */
public class TbTokenEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户token
     */
    private String token;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 过期时间
     */
    private Date expireTime;

    public TbTokenEntity() {
    }

    public TbTokenEntity(Integer userId, String token, Date updateTime, Date expireTime) {
        this.userId = userId;
        this.token = token;
        this.updateTime = updateTime;
        this.expireTime = expireTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "TbTokenEntity{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                ", updateTime=" + updateTime +
                ", expireTime=" + expireTime +
                '}';
    }
}
