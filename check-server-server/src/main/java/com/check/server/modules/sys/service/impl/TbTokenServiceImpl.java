package com.check.server.modules.sys.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.check.commom.constant.RedisKeys;
import com.check.commom.utils.JsonUtils;
import com.check.server.modules.sys.bean.TbTokenEntity;
import com.check.server.modules.sys.dao.ITbTokenDao;
import com.check.server.modules.sys.service.ITbTokenService;
import com.check.server.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 用户token操作接口实现类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 22:34:18
 */
@Service
public class TbTokenServiceImpl implements ITbTokenService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITbTokenDao tbTokenDao;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 根据token值获取token详细信息
     *
     * @param token token值
     * @return
     */
    @Override
    public TbTokenEntity getTbTokenByToken(String token) {
        // 从redis中获取token详情
        try {
            String s = redisUtils.hget(RedisKeys.SysKeys.TB_TOKEN, token);
            if (StringUtils.isNotBlank(s)) {
                return JsonUtils.jsonToPojo(s, TbTokenEntity.class);
            }
        } catch (Exception e) {
            logger.error("get token by redis exception, message: ", e);
        }

        // 从数据库中获取token信息
        TbTokenEntity tbToken = tbTokenDao.getTbTokenByToken(token);

        if (token == null) {
            return null;
        }

        // 将token放入redis中
        try {
            redisUtils.hset(RedisKeys.SysKeys.TB_TOKEN, token, JsonUtils.objectToJson(tbToken));
        } catch (Exception e) {
            logger.error("save tb_token to redis exception, message: ", e);
        }
        return tbToken;
    }
}
