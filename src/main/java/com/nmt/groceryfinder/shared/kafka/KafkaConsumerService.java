package com.nmt.groceryfinder.shared.kafka;

import com.nmt.groceryfinder.shared.circuitbreaker.CircuitBreakerAnnotation;
import com.nmt.groceryfinder.shared.cqrs.commands.CreateProductCommand;
import com.nmt.groceryfinder.shared.cqrs.handlers.impl.CreateProductHandler;
import com.nmt.groceryfinder.shared.health.HealthCheckInterceptor;
import com.nmt.groceryfinder.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/22/2024
 */
@Service
public class KafkaConsumerService {
    @Value("${topic.name.consumer}")
    private static final String topicName = "";

    @Value("${spring.elasticsearch.rest.uris}")
    private static final String serverUrl = "";

    private  final CreateProductHandler createProductHandler;
    public KafkaConsumerService(CreateProductHandler createProductHandler){
        this.createProductHandler = createProductHandler;
    }

    @HealthCheckInterceptor(serviceUrl = serverUrl)
    @CircuitBreakerAnnotation
    @KafkaListener(topics = topicName, groupId = "1")
    public void consumeProductMessage(String message) { //
        System.out.println("Consumed message: " + message);//
        CreateProductCommand data = JsonUtil.fromJson(message, CreateProductCommand.class);
        createProductHandler.execute(data);
    }
}
