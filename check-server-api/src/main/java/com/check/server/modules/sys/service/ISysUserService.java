package com.check.server.modules.sys.service;

import com.check.server.modules.sys.bean.SysUserEntity;
import com.check.server.modules.sys.vo.SysUserRowDataVo;

/**
 * @description: 用户信息操作接口
 * @author: Mr.ZHAO
 * @cereate: 2018/07/08 21:47:11
 */
public interface ISysUserService {

    /**
     * 保存用户信息
     *
     * @param sysUserEntity 用户详情
     * @return
     */
    Object save(SysUserEntity sysUserEntity);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return
     */
    Object update(SysUserEntity user);

    /**
     * 根据用户详情查询用户信息
     *
     * @param userId 用户id
     * @return
     */
    SysUserRowDataVo getUserByUserId(Integer userId);
}
