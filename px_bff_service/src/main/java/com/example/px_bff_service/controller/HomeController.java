package com.example.px_bff_service.controller;

import com.example.px_app_api.AppRpcService;
import com.example.px_app_api.dto.UserDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/frontend")
public class HomeController {

    @DubboReference
    private AppRpcService appRpcService;

    @GetMapping("/home")
    public UserDTO home() {
        UserDTO user = appRpcService.getUserInfo(369L);

        return user;
    }
}
