package com.nmt.groceryfinder.shared.circuitbreaker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/23/2024
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CircuitBreakerAnnotation {
    int failureThreshold() default 3;
    long resetTimeout() default 5000;
}
