package com.check.server.modules.sys.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.check.server.modules.sys.dao.ISysUserDao;
import com.check.server.modules.sys.service.ISysUserService;
import com.check.server.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 用户信息操作接口实现类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/08 21:48:10
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private ISysUserDao sysUserDao;

    @Autowired
    private RedisUtils redisUtils;
}
