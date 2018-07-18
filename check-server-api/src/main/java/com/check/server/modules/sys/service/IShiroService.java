package com.check.server.modules.sys.service;

import com.check.server.modules.sys.bean.TbTokenEntity;
import com.check.server.modules.sys.vo.SysUserRowDataVo;

import java.util.Set;

/**
 * @description: shiro权限控制接口
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 22:22:49
 */
public interface IShiroService {

    /**
     * 根据用户id查询用户详情
     *
     * @param userId 用户id
     * @return
     */
    SysUserRowDataVo getUserByUserId(Integer userId);

    /**
     * 查询用户权限列表
     *
     * @param userId 用户id
     * @return
     */
    Set<String> getUserPermissions(Integer userId);
}
