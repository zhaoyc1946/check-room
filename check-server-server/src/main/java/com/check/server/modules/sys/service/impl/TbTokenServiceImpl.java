package com.check.server.modules.sys.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.check.server.modules.sys.dao.ITbTokenDao;
import com.check.server.modules.sys.service.ITbTokenService;
import com.check.server.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 用户token操作接口实现类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 22:34:18
 */
@Service
public class TbTokenServiceImpl implements ITbTokenService {

    @Autowired
    private ITbTokenDao tbTokenDao;

    @Autowired
    private RedisUtils redisUtils;
}
