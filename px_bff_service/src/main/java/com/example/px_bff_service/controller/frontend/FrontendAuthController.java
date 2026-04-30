package com.example.px_bff_service.controller.frontend;

import com.example.px_app_api.dto.frontend.auth.LoginRequest;
import com.example.px_app_api.dto.frontend.auth.LoginResponse;
import com.example.px_app_api.dto.frontend.auth.RegisterRequest;
import com.example.px_app_api.rpc.frontend.AuthService;
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

@Tag(name = "前台认证", description = "前台用户注册、登录相关接口")
@RestController
@RequestMapping("/api/frontend/auth")
@RequiredArgsConstructor
public class FrontendAuthController {

    @DubboReference
    private final AuthService authService;

    @Operation(summary = "前台用户登录", description = "使用前台用户账号和密码登录，成功后返回用户信息和 JWT token。")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        RpcResponse<LoginResponse> rpc = authService.login(request);

        if (!rpc.isSuccess()) {
            return ApiResponse.error(rpc.getCode(), rpc.getMessage());
        }

        return ApiResponse.success(rpc.getData());
    }

    @Operation(summary = "前台用户注册", description = "注册新的前台用户账号，成功后返回用户信息和 JWT token。")
    @PostMapping("/register")
    public ApiResponse<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        RpcResponse<LoginResponse> rpc = authService.register(request);
        if (!rpc.isSuccess()) {
            return ApiResponse.error(rpc.getCode(), rpc.getMessage());
        }
        return ApiResponse.success(rpc.getData());
    }
}
