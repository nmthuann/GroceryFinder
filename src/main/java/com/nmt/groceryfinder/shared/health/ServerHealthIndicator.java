package com.nmt.groceryfinder.shared.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class ServerHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        try {
            String computerName = InetAddress.getLocalHost().getHostName();
            return Health.up().withDetail("computerName", computerName).build(); // status code: 200
        }catch(Exception e){
            return Health.down().withDetail("ERROR", e.getMessage()).build();
        }
    }
}
