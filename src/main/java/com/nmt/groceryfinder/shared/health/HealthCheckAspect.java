package com.nmt.groceryfinder.shared.health;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/24/2024
 */
@Aspect
@Slf4j
@Component
public class HealthCheckAspect {

    private final HealthCheckService healthCheckService;
    private final ElasticsearchHealthCheck elasticsearchHealthCheck;;

    @Autowired
    public HealthCheckAspect(
            HealthCheckService healthCheckService,
            ElasticsearchHealthCheck elasticsearchHealthCheck
    ) {
        this.healthCheckService = healthCheckService;
        this.elasticsearchHealthCheck = elasticsearchHealthCheck;
    }

    @Around("@annotation(healthCheckInterceptor)")
    public Object check(
            ProceedingJoinPoint joinPoint,
            HealthCheckInterceptor healthCheckInterceptor
    ) throws Throwable {
        switch (healthCheckInterceptor.healthCheckType()) {
            case SERVER:
                return handleServerHealthCheck(joinPoint, healthCheckInterceptor);
            case ELASTICSEARCH:
                return handleElasticsearchHealthCheck(joinPoint);
            default:
                throw new IllegalArgumentException(
                        "Unknown health check type: " + healthCheckInterceptor.healthCheckType()
                );
        }
    }

    private Object handleServerHealthCheck(
            ProceedingJoinPoint joinPoint,
            HealthCheckInterceptor healthCheckInterceptor
    ) throws Throwable {
        String serviceUrl = healthCheckInterceptor.serviceUrl();
        boolean retry = healthCheckInterceptor.retry();
        int maxRetries = retry ? 3 : 0;
        long retryDelay = 2000L;

        Mono<Boolean> isHealthy = healthCheckService.ping(serviceUrl, maxRetries, retryDelay);

        if (Boolean.FALSE.equals(isHealthy.block())) {
            log.error("{} is unavailable.", serviceUrl);
            throw new RuntimeException(serviceUrl + " is unavailable.");
        }
        return joinPoint.proceed();
    }

    private Object handleElasticsearchHealthCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        String healthStatus = elasticsearchHealthCheck.checkClusterHealth();
        if (healthStatus.contains("red")) {
            log.error("Elasticsearch cluster is unhealthy.");
            throw new RuntimeException("Elasticsearch cluster is unhealthy.");
        }
        return joinPoint.proceed();
    }


}


//    @Around("@annotation(healthCheckInterceptor)")
//    public Object check(
//            ProceedingJoinPoint joinPoint,
//            HealthCheckInterceptor healthCheckInterceptor
//    ) throws Throwable {
//
//        String serviceUrl = healthCheckInterceptor.serviceUrl();
//        boolean retry = healthCheckInterceptor.retry();
//        int maxRetries = 3;
//        long retryDelay = 2000L;
//
//        Mono<Boolean> isHealthy;
//        if (retry) {
//            isHealthy = this.healthCheckService.ping(serviceUrl, maxRetries, retryDelay);
//        } else {
//            isHealthy = this.healthCheckService.ping(serviceUrl, 0, 0);
//        }
//
//        if (Boolean.FALSE.equals(isHealthy.block())) {
//            log.error("{} is unavailable.", serviceUrl);
//            throw new RuntimeException(serviceUrl + " is unavailable.");
//        }
//
//        return joinPoint.proceed();
//    }