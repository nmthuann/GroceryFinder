package com.nmt.groceryfinder.shared.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Random;


/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/24/2024
 */
@Service
@Slf4j
public class HealthCheckService {

    private final WebClient.Builder webClientBuilder;
    private final Random random = new Random();

    @Autowired
    public HealthCheckService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Boolean> ping(String serviceUrl, int maxRetries, long retryDelay) {
        return Mono.defer(() -> webClientBuilder.build()
                .get()
                .uri(serviceUrl)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> response != null)
                .retryWhen(Retry.fixedDelay(maxRetries, Duration.ofMillis(0))
                        // Delay được quản lý trong retry logic
                        .doBeforeRetry(retrySignal -> {
                            // Tạo delay ngẫu nhiên trong khoảng 0 đến 1000 ms
                            long randomDelay = retryDelay + random.nextInt(1000);
                            log.error("Ping attempt {} failed. Retrying in {} ms...",
                                    retrySignal.totalRetries() + 1, randomDelay);
                            try {
                                Thread.sleep(randomDelay);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        })
                )
                .onErrorReturn(false));
    }
}
