package com.example.px_app_service.service.rpc.backend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.px_app_api.dto.backend.auth.LoginRequest;
import com.example.px_app_api.dto.backend.auth.LoginResponse;
import com.example.px_app_api.rpc.backend.AuthService;
import com.example.px_app_service.domain.Admin;
import com.example.px_app_service.mapper.AdminMapper;
import com.example.px_app_service.util.Google2FAUtil;
import com.example.px_app_service.util.JwtUtil;
import com.example.px_common.enums.BizCode;
import com.example.px_common.response.RpcResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@DubboService
@RequiredArgsConstructor
public class BackendAuthServiceImpl implements AuthService {

    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public RpcResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String code = loginRequest.getCode();

        //1 管理员是否存在
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Admin admin = adminMapper.selectOne(queryWrapper);
        if (admin == null) {
            return RpcResponse.error(BizCode.ADMIN_NOT_EXIST);
        }
        //2 密码是否正确
        Boolean isMatch = passwordEncoder.matches(password, admin.getPassword());
        if (!isMatch) {
            return RpcResponse.error(BizCode.ADMIN_PASSWORD_ERROR);
        }
        //3 验证码是否正确
        if (admin.getSwitchGoogle2fa() == 1) {
            if (Objects.equals(code, "")) {
                return RpcResponse.error(BizCode.ADMIN_INPUT_CODE);
            }

            if (Objects.equals(admin.getGoogle2faSecret(), "")) {
                return RpcResponse.error(BizCode.ADMIN_NOT_CONFIG_GOOGLE_2FA);
            }
            if (!Google2FAUtil.isTOTPCodeValid(code, admin.getGoogle2faSecret())) {
                return RpcResponse.error(BizCode.ADMIN_GOOGLE_CODE_ERROR);
            }
        }
        //4 生成token
        String token = jwtUtil.generateToken(admin, "admin");
        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .build();
        return RpcResponse.success(loginResponse);
    }
}
