package com.example.px_bff_service.controller.backend;

import com.example.px_app_api.dto.backend.auth.LoginRequest;
import com.example.px_app_api.dto.backend.auth.LoginResponse;
import com.example.px_app_api.rpc.backend.AuthService;
import com.example.px_common.response.ApiResponse;
import com.example.px_common.response.RpcResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "后台认证", description = "后台管理员登录相关接口")
@RestController
@RequestMapping("/api/backend/auth")
@RequiredArgsConstructor
public class BackendAuthController {
    @DubboReference
    private final AuthService authService;

    @Operation(summary = "后台管理员登录", description = "使用管理员账号、密码和 Google 验证码登录，成功后返回后台 JWT token。")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        RpcResponse<LoginResponse> loginResponse = authService.login(request);
        if (!loginResponse.isSuccess()) {
            return ApiResponse.error(loginResponse.getCode(), loginResponse.getMessage());
        }
        return ApiResponse.success(loginResponse.getData());
    }
}
