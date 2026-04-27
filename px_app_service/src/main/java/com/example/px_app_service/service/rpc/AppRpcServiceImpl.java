package com.example.px_app_service.service.rpc;

import com.example.px_app_api.AppRpcService;
import com.example.px_app_api.dto.UserDTO;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class AppRpcServiceImpl implements AppRpcService {
    @Override
    public UserDTO getUserInfo(Long userId) {
        UserDTO user = new UserDTO();
        user.setId(userId);
        user.setUsername("test-user-" + userId);
        return user;
    }
}
