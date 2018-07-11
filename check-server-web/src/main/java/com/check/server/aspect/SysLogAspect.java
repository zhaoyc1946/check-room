package com.check.server.aspect;

import com.alibaba.dubbo.config.annotation.Reference;
import com.check.annotation.SysLog;
import com.check.commom.utils.HttpContextUtils;
import com.check.commom.utils.IPUtils;
import com.check.commom.utils.JsonUtils;
import com.check.server.modules.sys.bean.SysLogEntity;
import com.check.server.modules.sys.service.ISysLogService;
import com.check.server.modules.sys.vo.SysUserRowDataVo;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @description: 系统日志, 切面处理类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/06 22:57:39
 */
@Aspect
@Component
public class SysLogAspect {

    @Reference
    private ISysLogService sysLogService;

    @Pointcut("@annotation(com.check.annotation.SysLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();

        // 执行时长
        long time = System.currentTimeMillis() - beginTime;

        // 保存日志
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint point, long time) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLog = new SysLogEntity();

        SysLog annotation = method.getAnnotation(SysLog.class);
        if (annotation != null) {
            // 注解上的描述
            sysLog.setOperation(annotation.value());
        }

        // 请求方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        // 获取请求参数
        Object[] args = point.getArgs();
        try {
            String params = JsonUtils.objectToJson(args);
            sysLog.setParams(params);
        } catch (Exception e) {
        }

        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

        // 获取IP
        sysLog.setIp(IPUtils.getIpAddr(request));

        // 获取用户名
        String userName = ((SysUserRowDataVo) SecurityUtils.getSubject().getPrincipal()).getUserName();
        sysLog.setUserName(userName);

        sysLog.setTime(time);
        sysLog.setCreateDate(new Date());

        // 保存系统日志
        sysLogService.save(sysLog);
    }
}
