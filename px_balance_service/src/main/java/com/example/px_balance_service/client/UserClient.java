package com.example.px_balance_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("px-app-service")
public interface UserClient {
    @GetMapping("/user/{userId}")
    String getUser(@PathVariable("userId") Long userId);

}
