package com.check.server.modules.sys.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.check.commom.constant.Constant;
import com.check.commom.constant.RedisKeys;
import com.check.commom.utils.JsonUtils;
import com.check.server.modules.sys.bean.TbTokenEntity;
import com.check.server.modules.sys.dao.ISysMenuDao;
import com.check.server.modules.sys.dao.ISysUserDao;
import com.check.server.modules.sys.dao.ITbTokenDao;
import com.check.server.modules.sys.service.IShiroService;
import com.check.server.modules.sys.vo.SysUserRowDataVo;
import com.check.server.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: shiro权限控制实现类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 22:23:38
 */
@Service
public class ShiroServiceImpl implements IShiroService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ITbTokenDao tbTokenDao;

    @Autowired
    private ISysUserDao sysUserDao;

    @Autowired
    private ISysMenuDao sysMenuDao;

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

    /**
     * 根据用户id查询用户详情
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
            logger.error("get sys_user by redis exception, message: ", e);
        }

        // 从数据库中获取用户详情
        SysUserRowDataVo sysUser = sysUserDao.findRowDataVOById(userId);

        if (sysUser != null) {
            // 放入redis中
            try {
                redisUtils.hset(RedisKeys.SysKeys.SYS_USER, userId.toString(), JsonUtils.objectToJson(sysUser));
            } catch (Exception e) {
                logger.error("save sys_user to redis exception, message: ", e);
            }
        }
        return sysUser;
    }

    /**
     * 查询用户权限列表
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Set<String> getUserPermissions(Integer userId) {
        List<String> permsList = null;

        // 超级管理员, 拥有所有权限
        if (userId == Constant.SUPER_ADMIN) {
            permsList = sysMenuDao.getAllPermsList();
        } else {
            permsList = sysUserDao.getAllPermsListByUserId(userId);
        }

        //用户权限列表
        Set<String> permsSet = new LinkedHashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }
}
