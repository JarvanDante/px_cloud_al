package com.example.px_app_api.rpc;

import com.example.px_app_api.dto.frontend.user.UserDTO;
import com.example.px_common.response.RpcResponse;

public interface UserService {
    //用户信息
    RpcResponse<UserDTO> userInfo(Long userId);
}
