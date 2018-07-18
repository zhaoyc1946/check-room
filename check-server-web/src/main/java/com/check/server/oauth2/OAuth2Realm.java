package com.check.server.oauth2;

import com.alibaba.dubbo.config.annotation.Reference;
import com.check.server.modules.sys.bean.TbTokenEntity;
import com.check.server.modules.sys.service.IShiroService;
import com.check.server.modules.sys.service.ITbTokenService;
import com.check.server.modules.sys.vo.SysUserRowDataVo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @description: 认证
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 21:48:24
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Reference
    private IShiroService shiroService;

    @Reference
    private ITbTokenService tbTokenService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserRowDataVo user = (SysUserRowDataVo)principals.getPrimaryPrincipal();
        //用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(user.getUserId());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        TbTokenEntity tbToken = tbTokenService.getTbTokenByToken(accessToken);
        //token失效
        if(tbToken == null || tbToken.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("Token is invalid, please log in again");
        }

        //查询用户信息
        SysUserRowDataVo user = shiroService.getUserByUserId(tbToken.getUserId());
        //账号锁定
        if(user.getStatus() == 0){
            throw new LockedAccountException("Account is locked, please contact administrator");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
