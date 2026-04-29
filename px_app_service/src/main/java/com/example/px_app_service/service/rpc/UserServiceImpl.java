package com.example.px_app_service.service.rpc;

import com.example.px_app_api.dto.user.UserDTO;
import com.example.px_app_api.rpc.UserService;
import com.example.px_app_service.domain.User;
import com.example.px_app_service.mapper.UserMapper;
import com.example.px_common.enums.BizCode;
import com.example.px_common.response.RpcResponse;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@DubboService
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.of("Asia/Shanghai"));

    private final UserMapper userMapper;

    @Override
    public RpcResponse<UserDTO> userInfo(Long userId) {
        if (userId == null) {
            return RpcResponse.error(BizCode.USER_NOT_EXIST);
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            return RpcResponse.error(BizCode.USER_NOT_EXIST);
        }
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .siteId(user.getSiteId())
                .gradeId(user.getGradeId())
                .agentId(user.getAgentId())
                .inviterId(user.getInviterId())
                .username(user.getUsername())
                .mobile(user.getMobile())
                .email(user.getEmail())
                .lastLoginIp(user.getLastLoginIp())
                .lastLoginTime(formatDateTime(user.getLastLoginTime()))
                .build();
        return RpcResponse.success(userDTO);
    }

    private String formatDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return DATE_TIME_FORMATTER.format(instant);
    }
}
