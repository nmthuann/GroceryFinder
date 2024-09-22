package com.nmt.groceryfinder.shared.kafka;

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
    @Value("${topic.name.consumer")
    private String topicName;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "1")
    public void consumeMessage(String message) {
        System.out.println("Consumed message: " + message);//
    }
}
