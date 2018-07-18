package com.check.server.modules.sys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.check.server.modules.sys.bean.SysUserEntity;
import com.check.server.modules.sys.service.ISysUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 用户操作Controller
 * @author: Mr.ZHAO
 * @cereate: 2018/07/08 21:38:05
 */
@RestController
@RequestMapping("/tokens/sys/user")
public class SysUserController extends AbstractController {

    @Reference
    private ISysUserService sysUserService;

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return
     */
    public Object save(SysUserEntity user) {
        return sysUserService.save(user);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return
     */
    public Object update(SysUserEntity user) {
        return sysUserService.update(user);
    }


}
