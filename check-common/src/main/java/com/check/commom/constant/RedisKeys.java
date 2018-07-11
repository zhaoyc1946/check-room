package com.check.commom.constant;

/**
 * @description: redis key常量
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 17:23:50
 */
public class RedisKeys {

    public class SysKeys {

        /**
         * 用户token hash键, 字段为token, 值为token详情
         */
        public static final String TB_TOKEN = "to_token";

        /**
         * 用户详情hash键, 字段为user_id, 值为用户详情
         */
        public static final String SYS_USER = "sys_user";
    }
}
