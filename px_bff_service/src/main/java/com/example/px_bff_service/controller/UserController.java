package com.example.px_bff_service.controller;

import com.example.px_app_api.dto.user.UserDTO;
import com.example.px_app_api.rpc.UserService;
import com.example.px_common.response.ApiResponse;
import com.example.px_common.response.RpcResponse;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/frontend/user")
public class UserController {

    @DubboReference
    private final UserService userService;

    @GetMapping("/user-info")
    public ApiResponse<UserDTO> userInfo(@RequestHeader("X-User-Id") Long userId) {
        RpcResponse<UserDTO> rpc = userService.userInfo(userId);
        if (!rpc.isSuccess()) {
            return ApiResponse.error(rpc.getCode(), rpc.getMessage());
        }
        return ApiResponse.success(rpc.getData());
    }
}
