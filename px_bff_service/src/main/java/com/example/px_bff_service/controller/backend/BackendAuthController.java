package com.example.px_bff_service.controller.backend;

import com.example.px_app_api.dto.backend.auth.LoginRequest;
import com.example.px_app_api.dto.backend.auth.LoginResponse;
import com.example.px_app_api.rpc.backend.AuthService;
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
@RequestMapping("/api/backend/auth")
@RequiredArgsConstructor
public class BackendAuthController {
    @DubboReference
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse login(@Valid @RequestBody LoginRequest request) {
        RpcResponse<LoginResponse> loginResponse = authService.login(request);
        if (!loginResponse.isSuccess()) {
            return ApiResponse.error(loginResponse.getCode(), loginResponse.getMessage());
        }
        return ApiResponse.success(loginResponse.getData());
    }
}
