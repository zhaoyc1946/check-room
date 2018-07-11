package com.check.server.modules.sys.bean;

import java.io.Serializable;

/**
 * @description: 部门管理实体类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/03 21:45:56
 */
public class SysDeptEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 上级部门ID, 一级部门为0
     */
    private Integer parentId;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态  0：已删除  1: 正常, 默认为1
     */
    private Integer statues;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatues() {
        return statues;
    }

    public void setStatues(Integer statues) {
        this.statues = statues;
    }

    @Override
    public String toString() {
        return "SysDeptEntity{" +
                "deptId=" + deptId +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", statues=" + statues +
                '}';
    }
}
