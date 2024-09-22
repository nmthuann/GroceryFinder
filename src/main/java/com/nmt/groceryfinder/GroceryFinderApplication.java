package com.nmt.groceryfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

//@EnableKafka
@SpringBootApplication
public class GroceryFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroceryFinderApplication.class, args);
    }

}
