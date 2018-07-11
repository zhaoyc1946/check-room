package com.check.server.modules.sys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
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
}
