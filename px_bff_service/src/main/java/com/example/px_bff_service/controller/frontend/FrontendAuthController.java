package com.example.px_bff_service.controller.frontend;

import com.example.px_app_api.dto.frontend.auth.LoginRequest;
import com.example.px_app_api.dto.frontend.auth.LoginResponse;
import com.example.px_app_api.dto.frontend.auth.RegisterRequest;
import com.example.px_app_api.rpc.frontend.AuthService;
import com.example.px_common.response.ApiResponse;
import com.example.px_common.response.RpcResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/frontend/auth")
@RequiredArgsConstructor
public class FrontendAuthController {

    @DubboReference
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse login(@Valid @RequestBody LoginRequest request) {
        RpcResponse<LoginResponse> rpc = authService.login(request);

        if (!rpc.isSuccess()) {
            return ApiResponse.error(rpc.getCode(), rpc.getMessage());
        }

        return ApiResponse.success(rpc.getData());
    }

    @PostMapping("/register")
    public ApiResponse register(@Valid @RequestBody RegisterRequest request) {
        RpcResponse<LoginResponse> rpc = authService.register(request);
        if (!rpc.isSuccess()) {
            return ApiResponse.error(rpc.getCode(), rpc.getMessage());
        }
        return ApiResponse.success(rpc.getData());
    }
}
