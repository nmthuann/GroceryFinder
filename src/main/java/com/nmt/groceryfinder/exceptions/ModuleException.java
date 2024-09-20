package com.nmt.groceryfinder.exceptions;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/19/2024
 */
public class ModuleException extends Exception {
    public ModuleException() {
    }

    public ModuleException(String message) {
        super(message);
    }
}
