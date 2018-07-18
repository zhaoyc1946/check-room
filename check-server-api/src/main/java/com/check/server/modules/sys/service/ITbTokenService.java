package com.check.server.modules.sys.service;

import com.check.server.modules.sys.bean.TbTokenEntity;

/**
 * @description: 用户token操作接口
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 22:33:42
 */
public interface ITbTokenService {


    /**
     * 根据token值获取token详细信息
     *
     * @param token token值
     * @return
     */
    TbTokenEntity getTbTokenByToken(String token);
}
