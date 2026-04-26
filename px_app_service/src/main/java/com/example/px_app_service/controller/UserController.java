package com.example.px_app_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user/{userId}")
    public String test(@PathVariable("userId") Long userId) {
        return "用户信息：" + userId;
    }
}
