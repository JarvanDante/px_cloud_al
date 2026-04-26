package com.example.px_balance_service.controller;

import com.example.px_balance_service.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final UserClient userClient;

    @GetMapping("/order/{userId}")
    public String getOrder(@PathVariable("userId") Long userId) {

        String user = userClient.getUser(userId);
        return "订单服务调用用户服务，用户ID：" + user;
    }
}
