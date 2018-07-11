package com.check.commom.utils;

import java.util.Collection;
import java.util.Collections;

/**
 * @description: 封装分页数据工具类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 17:48:06
 */
public class PageUtils {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 每页记录数
     */
    private int limit;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页数
     */
    private int page;
    /**
     * 列表数据
     */
    private Collection<?> list;

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param limit      每页记录数
     * @param page       当前页数
     */
    public PageUtils(Collection<?> list, int totalCount, int limit, int page) {
        this.list = list;
        this.totalCount = totalCount;
        this.limit = limit;
        this.page = page;
        this.totalPage = (int) Math.ceil((double) totalCount / limit);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Collection<?> getList() {
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public void setList(Collection<?> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageUtils{" +
                "totalCount=" + totalCount +
                ", limit=" + limit +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", list=" + list +
                '}';
    }
}
