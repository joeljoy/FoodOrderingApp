package org.upgrad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FoodOrderingApp {
    public static void main(String[] args) {
        SpringApplication.run(FoodOrderingApp.class, args);
    }
}
