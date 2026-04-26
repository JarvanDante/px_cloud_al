package com.example.px_balance_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BalanceApplication {
    public static void main(String[] args) {
        System.out.println("服务：px-balance-service");
        SpringApplication.run(BalanceApplication.class, args);
    }
}
