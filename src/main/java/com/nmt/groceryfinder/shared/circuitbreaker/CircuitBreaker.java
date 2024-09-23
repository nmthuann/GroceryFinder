package com.nmt.groceryfinder.shared.circuitbreaker;

import com.nmt.groceryfinder.shared.circuitbreaker.states.ClosedState;
import com.nmt.groceryfinder.shared.circuitbreaker.states.HalfOpenState;
import com.nmt.groceryfinder.shared.circuitbreaker.states.OpenState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/23/2024
 */
@Getter
@Setter
@NoArgsConstructor
@Component
public class CircuitBreaker {
    private ICircuitBreakerState circuitBreakerState;
    private int failureCount;
    private int failureThreshold; // số lần thất bại cho phép
    private long lastFailureTime;
    private long resetTimeout;

    public CircuitBreaker(int failureThreshold, long resetTimeout){
        this.circuitBreakerState = new ClosedState();
        this.failureThreshold = failureThreshold;
        this.resetTimeout = resetTimeout;
        reset();
    }

    public void reset() {
        failureCount = 0;
        circuitBreakerState = new ClosedState();
    }

    public void call(Runnable runnable){
        circuitBreakerState.call(this, runnable);
    }

    public void recordFailure(){
        failureCount++;
        lastFailureTime = System.currentTimeMillis();
        if(failureCount >= failureThreshold){
            setCircuitBreakerState(new OpenState());
        }
    }

    /**
     *  Nếu khoảng thời gian từ lần thất bại cuối cùng (lastFailureTime) lớn hơn resetTimeout
     *  -> true
     * @return boolean
     */
    public boolean canAttemptReset() {
        return System.currentTimeMillis() - lastFailureTime > resetTimeout;
    }

    public void attemptReset() {
        if (canAttemptReset()) {
            setCircuitBreakerState(new HalfOpenState());
        }
    }
}
