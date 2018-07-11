package com.check.server.handler;

import com.check.commom.utils.ResultUtils;
import com.check.exception.CustomException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 异常处理器
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 22:17:40
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public ResultUtils handleRRException(CustomException e){
        ResultUtils r = new ResultUtils();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());

        return r;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultUtils handleDuplicateKeyException(DuplicateKeyException e){
        logger.error(e.getMessage(), e);
        return ResultUtils.error("数据库中已存在该记录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResultUtils handleAuthorizationException(AuthorizationException e){
        logger.error(e.getMessage(), e);
        return ResultUtils.error("没有权限，请联系管理员授权");
    }

    @ExceptionHandler(Exception.class)
    public ResultUtils handleException(Exception e){
        logger.error(e.getMessage(), e);
        return ResultUtils.error();
    }

}
