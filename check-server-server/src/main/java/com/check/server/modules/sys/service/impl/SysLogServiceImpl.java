package com.check.server.modules.sys.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.check.server.modules.sys.bean.SysLogEntity;
import com.check.server.modules.sys.dao.ISysLogDao;
import com.check.server.modules.sys.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 日志信息操作接口实现类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/08 21:50:04
 */
@Service
public class SysLogServiceImpl implements ISysLogService {

    @Autowired
    private ISysLogDao sysLogDao;

    @Override
    public int save(SysLogEntity entity) {
        return sysLogDao.save(entity);
    }
}
