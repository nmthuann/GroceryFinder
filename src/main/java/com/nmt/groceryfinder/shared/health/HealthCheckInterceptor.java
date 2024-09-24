package com.nmt.groceryfinder.shared.health;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/24/2024
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HealthCheckInterceptor {
    String serviceUrl();
    boolean retry() default false;
}
