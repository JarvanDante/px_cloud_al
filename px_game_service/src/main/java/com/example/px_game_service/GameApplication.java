package com.example.px_game_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameApplication {
    public static void main(String[] args) {
        System.out.println("服务：px-game-service");
        SpringApplication.run(GameApplication.class);
    }
}
