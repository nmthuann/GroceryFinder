package com.nmt.groceryfinder.shared.circuitbreaker.states;

import com.nmt.groceryfinder.shared.circuitbreaker.CircuitBreaker;
import com.nmt.groceryfinder.shared.circuitbreaker.ICircuitBreakerState;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/23/2024
 */
public class HalfOpenState implements ICircuitBreakerState {
    @Override
    public void call(CircuitBreaker circuitBreaker, Runnable protectedCall) {
        try {
            protectedCall.run();
            circuitBreaker.reset();
        } catch (Exception e) {
            circuitBreaker.recordFailure();
            System.out.println("Half-Open state failed. Circuit re-opening.....");
            circuitBreaker.setCircuitBreakerState(new OpenState());
        }
    }
}
