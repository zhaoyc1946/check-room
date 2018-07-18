package com.check.server.modules.api.resolver;

import com.alibaba.dubbo.config.annotation.Reference;
import com.check.annotation.LoginUser;
import com.check.server.modules.api.interceptor.AuthorizationInterceptor;
import com.check.server.modules.sys.bean.SysUserEntity;
import com.check.server.modules.sys.service.ISysUserService;
import com.check.server.modules.sys.vo.SysUserRowDataVo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @description: 有@LoginUser注解的方法参数，注入当前登录用户
 * @author: Mr.ZHAO
 * @cereate: 2018/07/17 22:51:55
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Reference
    private ISysUserService sysUserService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(SysUserEntity.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (object == null) {
            return null;
        }

        //获取用户信息
        SysUserRowDataVo user = sysUserService.getUserByUserId((Integer) object);

        return user;
    }
}
