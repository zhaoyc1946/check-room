package com.check.commom.utils;

import java.util.List;

/**
 * @description: 查询接口工具类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 17:31:20
 */
public interface BaseDaoUtils<T> {

    /**
     * 单个保存信息
     *
     * @param t
     * @return
     */
    int save(T t);

    /**
     * 批量保存信息
     *
     * @param list
     * @return
     */
    int saveBatch(List<T> list);

    /**
     * 修改信息
     *
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 删除单个信息
     *
     * @param id
     * @return
     */
    int delete(final Integer id);

    /**
     * 批量删除信息
     *
     * @param id
     * @return
     */
    int deleteBatch(final Integer[] id);

    /**
     * 根据ID获取行数据VO
     *
     * @param id
     * @return
     */
    <VO> VO findRowDataVOById(final Integer id);

    /**
     * 分页获取当前页表格数据
     *
     * @param query 分页及查询条件
     * @return
     */
    <VO> List<VO> listWithPagerQuery(final QueryUtils query);

    /**
     * 获取符合查询条件的总记录数
     *
     * @param query 查询条件
     * @return
     */
    Integer countWithQuery(final QueryUtils query);

}
