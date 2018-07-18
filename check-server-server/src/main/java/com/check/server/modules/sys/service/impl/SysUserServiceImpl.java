package com.check.server.modules.sys.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.check.commom.constant.RedisKeys;
import com.check.commom.utils.JsonUtils;
import com.check.commom.utils.ResultUtils;
import com.check.server.modules.sys.bean.SysMenuEntity;
import com.check.server.modules.sys.bean.SysUserEntity;
import com.check.server.modules.sys.dao.ISysUserDao;
import com.check.server.modules.sys.service.ISysUserService;
import com.check.server.modules.sys.vo.SysUserRowDataVo;
import com.check.server.utils.RedisUtils;
import com.check.server.utils.mq.redis.RedisDeleteKeyProduct;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * @description: 用户信息操作接口实现类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/08 21:48:10
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISysUserDao sysUserDao;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 保存用户信息
     *
     * @param user 用户详情
     * @return
     */
    @Override
    public Object save(SysUserEntity user) {
        // 生成加密盐
        String salt = UUID.randomUUID().toString().replace("-", "");

        user.setCreateTime(new Date());
        user.setSalt(salt);
        user.setStatus((byte) 1);

        String password = new Sha256Hash(user.getPassword(), salt).toHex();
        user.setPassword(password);

        // 保存信息到数据库
        sysUserDao.save(user);

        return ResultUtils.ok();
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public Object update(SysUserEntity user) {

        String password = user.getPassword();

        if (StringUtils.isNotBlank(password)) {
            String salt = UUID.randomUUID().toString().replace("-", "");
            user.setPassword(new Sha256Hash(password, salt).toHex());
            user.setSalt(salt);
        }

        sysUserDao.update(user);

        // 删除redis中的数据
        try {
            redisUtils.hdel(RedisKeys.SysKeys.SYS_USER, user.getUserId().toString());
        } catch (Exception e) {
            logger.error("delete user from redis excepion, use mq delete, exception message: ", e);
            RedisDeleteKeyProduct.deleteHashKey(RedisKeys.SysKeys.SYS_USER, user.getUserId().toString());
        }
        return ResultUtils.ok();
    }

    /**
     * 根据用户详情查询用户信息
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public SysUserRowDataVo getUserByUserId(Integer userId) {
        // 从redis中获取用户详情
        try {
            String hget = redisUtils.hget(RedisKeys.SysKeys.SYS_USER, userId.toString());
            if (StringUtils.isNotBlank(hget)) {
                return JsonUtils.jsonToPojo(hget, SysUserRowDataVo.class);
            }
        } catch (Exception e) {
            logger.error("get user info from redis exception, message: ", e);
        }

        // 从数据库中获取
        SysUserRowDataVo userRowDataVo = sysUserDao.findRowDataVOById(userId);

        // 将用户信息保存到redis中
        if (userRowDataVo != null) {
            try {
                redisUtils.hset(RedisKeys.SysKeys.SYS_USER, userId.toString(), JsonUtils.objectToJson(userRowDataVo));
            } catch (Exception e) {
                logger.error("save user to redis exception, message: ", e);
            }
        }
        return userRowDataVo;
    }
}
