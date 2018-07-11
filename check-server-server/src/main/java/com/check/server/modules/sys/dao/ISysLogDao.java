package com.check.server.modules.sys.dao;

import com.check.commom.utils.BaseDaoUtils;
import com.check.server.modules.sys.bean.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 系统日志操作Dao
 * @author: Mr.ZHAO
 * @cereate: 2018/07/03 22:24:40
 */
@Mapper
public interface ISysLogDao extends BaseDaoUtils<SysLogEntity> {
}
