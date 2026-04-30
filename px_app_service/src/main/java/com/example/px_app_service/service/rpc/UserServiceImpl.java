package com.example.px_app_service.service.rpc;

import com.example.px_app_api.dto.frontend.user.UserResponse;
import com.example.px_app_api.rpc.UserService;
import com.example.px_app_service.domain.User;
import com.example.px_app_service.mapper.UserMapper;
import com.example.px_common.enums.BizCode;
import com.example.px_common.response.RpcResponse;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;


@DubboService
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public RpcResponse<UserResponse> userInfo(Long userId) {
        if (userId == null) {
            return RpcResponse.error(BizCode.USER_NOT_EXIST);
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            return RpcResponse.error(BizCode.USER_NOT_EXIST);
        }
        UserResponse userDTO = UserResponse.builder()
                .id(user.getId())
                .siteId(user.getSiteId())
                .gradeId(user.getGradeId())
                .agentId(user.getAgentId())
                .inviterId(user.getInviterId())
                .username(user.getUsername())
                .mobile(user.getMobile())
                .email(user.getEmail())
                .lastLoginIp(user.getLastLoginIp())
                .lastLoginTime(user.getLastLoginTime())
                .build();
        return RpcResponse.success(userDTO);
    }

}
