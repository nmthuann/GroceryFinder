package com.nmt.groceryfinder.shared.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/11/2024
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggingInterceptor {
}
