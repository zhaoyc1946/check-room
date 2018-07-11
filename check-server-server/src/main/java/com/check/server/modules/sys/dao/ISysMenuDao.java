package com.check.server.modules.sys.dao;

import com.check.commom.utils.BaseDaoUtils;
import com.check.server.modules.sys.bean.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 系统菜单操作Dao接口
 * @author: Mr.ZHAO
 * @cereate: 2018/07/05 21:12:30
 */
@Mapper
public interface ISysMenuDao extends BaseDaoUtils<SysMenuEntity>{

    /**
     * 查询所有权限
     *
     * @return
     */
    List<String> getAllPermsList();
}
