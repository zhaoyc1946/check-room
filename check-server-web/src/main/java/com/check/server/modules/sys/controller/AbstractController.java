package com.check.server.modules.sys.controller;

import com.check.server.modules.sys.vo.SysUserRowDataVo;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUserRowDataVo getUser() {
        return (SysUserRowDataVo) SecurityUtils.getSubject().getPrincipal();
    }

    protected Integer getUserId() {
        return getUser().getUserId();
    }
}
