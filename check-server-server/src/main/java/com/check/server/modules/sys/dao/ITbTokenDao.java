package com.check.server.modules.sys.dao;

import com.check.commom.utils.BaseDaoUtils;
import com.check.server.modules.sys.bean.TbTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 用户token操作Dao
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 22:36:40
 */
@Mapper
public interface ITbTokenDao extends BaseDaoUtils<TbTokenEntity> {

    /**
     * 根据token值获取token详细信息
     *
     * @param token token值
     * @return
     */
    TbTokenEntity getTbTokenByToken(String token);
}
