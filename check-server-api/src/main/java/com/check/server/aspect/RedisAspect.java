package com.check.server.aspect;

import com.check.exception.CustomException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description: redis切面处理类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 17:18:48
 */
@Aspect
@Configuration
public class RedisAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 是否开启redis缓存, true开启, false关闭
     */
    @Value("${spring.redis.open}")
    private boolean open;

    @Around("execution(* com.check.server.utils.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if (open) {
            try {
                result = point.proceed();
            } catch (Exception e) {
                logger.error("redis error", e);
                throw new CustomException("Redis Service Exception");
            }
        }
        return result;
    }


}
