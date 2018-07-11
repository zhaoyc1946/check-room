package com.check.server.modules.sys.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 系统角色实体类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/03 22:01:44
 */
public class SysRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色Id
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所属部门ID
     */
    private Integer deptId;

    /**
     * 创建时间
     */
    private Date cereateime;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Date getCereateime() {
        return cereateime;
    }

    public void setCereateime(Date cereateime) {
        this.cereateime = cereateime;
    }

    @Override
    public String toString() {
        return "SysRoleEntity{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", remark='" + remark + '\'' +
                ", deptId=" + deptId +
                ", cereateime=" + cereateime +
                '}';
    }
}
