package com.check.server.modules.sys.service;

import com.check.server.modules.sys.bean.SysLogEntity;

/**
 * @description: 系统日志操作service接口
 * @author: Mr.ZHAO
 * @cereate: 2018/07/08 16:38:20
 */
public interface ISysLogService {

    /**
     * 保存日志信息
     *
     * @param entity 日志信息
     * @return 影响的数据库行数
     */
    int save(SysLogEntity entity);
}
