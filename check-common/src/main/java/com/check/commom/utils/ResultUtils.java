package com.check.commom.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 封装返回值对象工具类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 17:54:21
 */
public class ResultUtils extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    private static final String MSG = "msg";
    private static final String CODE = "code";
    private static final String RESULT = "result";

    public ResultUtils() {
        put(MSG, "");
        put(CODE, 1);
        put(RESULT, "");
    }

    public static ResultUtils error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static ResultUtils error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static ResultUtils error(int code, String msg) {
        ResultUtils r = new ResultUtils();
        r.put(CODE, code);
        r.put(MSG, msg);
        r.put(RESULT, "");
        return r;
    }

    public static ResultUtils ok(String msg) {
        ResultUtils r = new ResultUtils();
        r.put(MSG, msg);
        r.put(CODE, 1);
        r.put(RESULT, "");
        return r;
    }

    public static ResultUtils ok(Map<String, Object> map) {
        ResultUtils r = new ResultUtils();
        r.putAll(map);
        return r;
    }

    public static ResultUtils ok() {
        return new ResultUtils();
    }

    public ResultUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
