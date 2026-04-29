package com.example.px_app_api.rpc;

import com.example.px_app_api.dto.auth.LoginRequest;
import com.example.px_app_api.dto.auth.RegisterRequest;
import com.example.px_common.response.RpcResponse;

public interface AuthService {
    // 登录
    RpcResponse login(LoginRequest request);

    // 注册
    RpcResponse register(RegisterRequest request);
}
