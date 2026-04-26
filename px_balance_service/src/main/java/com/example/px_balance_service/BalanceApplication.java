package com.example.px_balance_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.example.px_balance_service.client")
public class BalanceApplication {
    public static void main(String[] args) {
        System.out.println("服务：px-balance-service");
        SpringApplication.run(BalanceApplication.class, args);
    }
}
