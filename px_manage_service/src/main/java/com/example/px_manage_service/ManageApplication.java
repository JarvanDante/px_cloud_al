package com.example.px_manage_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManageApplication {
    public static void main(String[] args) {
        System.out.println("服务：px-manage-service");
        SpringApplication.run(ManageApplication.class, args);
    }
}
