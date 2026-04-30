package com.example.px_app_api.rpc.backend;

import com.example.px_app_api.dto.backend.auth.LoginRequest;
import com.example.px_app_api.dto.backend.auth.LoginResponse;
import com.example.px_common.response.RpcResponse;

public interface AuthService {
    //登录
    RpcResponse<LoginResponse> login(LoginRequest loginRequest);

}
