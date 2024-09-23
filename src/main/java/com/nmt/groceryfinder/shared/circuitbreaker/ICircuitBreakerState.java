package com.nmt.groceryfinder.shared.circuitbreaker;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/23/2024
 */
public interface ICircuitBreakerState {
    void call(CircuitBreaker circuitBreaker, Runnable protectedCall);
}
