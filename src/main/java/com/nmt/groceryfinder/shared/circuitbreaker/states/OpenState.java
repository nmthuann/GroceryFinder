package com.nmt.groceryfinder.shared.circuitbreaker.states;

import com.nmt.groceryfinder.shared.circuitbreaker.CircuitBreaker;
import com.nmt.groceryfinder.shared.circuitbreaker.ICircuitBreakerState;


/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/23/2024
 */
public class OpenState implements  ICircuitBreakerState {

    @Override
    public void call(CircuitBreaker circuitBreaker, Runnable protectedCall) {
        System.out.println("Circuit is open. No requests allowed.");
        circuitBreaker.attemptReset();
    }
}
