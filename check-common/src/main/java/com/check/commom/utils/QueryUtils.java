package com.check.commom.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: 封装查询操作工具类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 17:37:20
 */
public class QueryUtils extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private int page;
    /**
     * 每页显示信息数量
     */
    private int limit;

    public QueryUtils(Map<String, Object> params) {
        this.putAll(params);
        // 分页参数
        this.page = params.get("page") == null ? 1 : Integer.parseInt(params.get("page").toString());
        this.limit = params.get("limit") == null ? 10 : Integer.parseInt(params.get("limit").toString());

        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String) params.get("sidx");
        String order = (String) params.get("order");
        if (StringUtils.isNotBlank(sidx)) {
            this.put("sidx", SQLFilterUtils.sqlInject(sidx));
        }
        if (StringUtils.isNotBlank(order)) {
            this.put("order", SQLFilterUtils.sqlInject(order));
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
