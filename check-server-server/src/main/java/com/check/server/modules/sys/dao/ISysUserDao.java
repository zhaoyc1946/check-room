package com.check.server.modules.sys.dao;

import com.check.commom.utils.BaseDaoUtils;
import com.check.server.modules.sys.bean.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 用户操作Dao接口
 * @author: Mr.ZHAO
 * @cereate: 2018/07/02 21:31:56
 */
@Mapper
public interface ISysUserDao extends BaseDaoUtils<SysUserEntity> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> getAllPermsListByUserId(Integer userId);
}
