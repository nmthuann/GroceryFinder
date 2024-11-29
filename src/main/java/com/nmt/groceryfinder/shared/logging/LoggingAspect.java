package com.nmt.groceryfinder.shared.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/10/2024
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("@annotation(com.nmt.groceryfinder.shared.logging.LoggingInterceptor)")
    public void methodAnnotatedWithLoggable() {
    }

    @Around("@annotation(com.nmt.groceryfinder.shared.logging.LoggingInterceptor)")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String author = "PUBLIC"; // Default value
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            author = ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes != null ?
                (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST) : null;
        String requestURI = request != null ? request.getRequestURI() : "unknown";
        String methodType = request != null ? request.getMethod() : "unknown";
        String clientIp = request != null ? request.getRemoteAddr() : "unknown";

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;

        log.info("Author: {}, Method: {}, URI: {}, IP: {}, Execution time: {}ms",
                author, methodType, requestURI, clientIp, executeTime);
        return result;
    }

    @AfterThrowing(pointcut = "methodAnnotatedWithLoggable()", throwing = "ex")
    public void logExceptions(JoinPoint joinPoint, Throwable ex) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        String className = methodSignature.getDeclaringTypeName();
        log.error("Exception in {}.{}() with cause = '{}' and exception = '{}'",
                className, methodName, (ex.getCause() != null ? ex.getCause() : "NULL"), ex.getMessage());
    }
}
