package com.nmt.groceryfinder.shared.ratelimit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/29/2024
 */
@Aspect
@Component
public class RateLimiterAspect {
    private final RateLimiterImpl rateLimiter;
    @Autowired
    public RateLimiterAspect(RateLimiterImpl rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Around("@annotation(com.nmt.groceryfinder.shared.ratelimit.RateLimiter)")
    public Object applyRateLimiting(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimiter rateLimiterAnnotation = method.getAnnotation(RateLimiter.class);

        int limit = rateLimiterAnnotation.limit();
        long ttl = rateLimiterAnnotation.ttl();

        String key = signature.getMethod().getName();

        if (!rateLimiter.isAllowed(key, limit, ttl)) {
            throw new RateLimitExceededException("Rate limit exceeded. Please try again later.");
        }

        return joinPoint.proceed();
    }

}
