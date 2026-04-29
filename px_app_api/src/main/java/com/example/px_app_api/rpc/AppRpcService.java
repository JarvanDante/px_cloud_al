package com.example.px_app_api.rpc;

import com.example.px_app_api.dto.user.UserDTO;

public interface AppRpcService {
    UserDTO getUserInfo(Long userId);
}