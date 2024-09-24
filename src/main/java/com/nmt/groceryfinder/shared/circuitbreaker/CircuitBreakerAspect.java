package com.nmt.groceryfinder.shared.circuitbreaker;

import com.nmt.groceryfinder.shared.circuitbreaker.states.OpenState;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/23/2024
 */
@Aspect
@Component
@Slf4j
public class CircuitBreakerAspect {
    private final CircuitBreaker circuitBreaker;

    @Autowired
    public CircuitBreakerAspect(CircuitBreaker circuitBreaker){
        this.circuitBreaker = circuitBreaker;
    }

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    @Around("@annotation(com.nmt.groceryfinder.shared.circuitbreaker.CircuitBreakerAnnotation)")
    public Object handleCircuitBreaker(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        CircuitBreakerAnnotation annotation = method.getAnnotation(CircuitBreakerAnnotation.class);
        configureCircuitBreaker(annotation);


        if (circuitBreaker.getCircuitBreakerState() instanceof OpenState) {
            log.warn("Circuit breaker is in OPEN state, request is blocked.");
            throw new RuntimeException("Service is unavailable due to circuit breaker being OPEN.");
        }

        circuitBreaker.call(() -> {
            boolean isKafkaAvailable = pingKafka();
            if (!isKafkaAvailable) {
                throw new RuntimeException("Kafka is unavailable.");
            }
        });

        return joinPoint.proceed();
    }


    private void configureCircuitBreaker(CircuitBreakerAnnotation annotation) {
        circuitBreaker.setFailureThreshold(annotation.failureThreshold());
        circuitBreaker.setResetTimeout(annotation.resetTimeout());
    }

    private boolean pingKafka() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);

        return retryKafkaCheck(() -> {
            try (AdminClient adminClient = AdminClient.create(properties)) {
                DescribeClusterResult clusterResult = adminClient.describeCluster();
                clusterResult.nodes().get();
                log.info("Kafka is available.");
                return true;
            }
        });
    }

    private boolean retryKafkaCheck(Callable<Boolean> kafkaCheck) {
        int maxRetries = 5;
        int retryCount = 0;
        long retryDelay = 2000L; // 2 seconds

        while (retryCount < maxRetries) {
            try {
                return kafkaCheck.call();
            } catch (Exception e) {
                retryCount++;
                log.error("Kafka is unavailable (attempt {} of {}): {}", retryCount, maxRetries, e.getMessage());

                if (retryCount >= maxRetries) {
                    log.error("Kafka is unavailable after {} attempts.", maxRetries);
                    return false;
                }

                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.error("Retry interrupted: {}", ie.getMessage());
                    return false;
                }
            }
        }
        return false;
    }
}
