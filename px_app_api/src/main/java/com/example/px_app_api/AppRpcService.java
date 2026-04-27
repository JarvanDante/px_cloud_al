package com.example.px_app_api;

import com.example.px_app_api.dto.UserDTO;

public interface AppRpcService {
    UserDTO getUserInfo(Long userId);
}