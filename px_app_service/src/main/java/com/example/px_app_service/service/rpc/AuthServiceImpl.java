package com.example.px_app_service.service.rpc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.px_app_api.dto.auth.LoginRequest;
import com.example.px_app_api.dto.auth.LoginResponse;
import com.example.px_app_api.dto.auth.RegisterRequest;
import com.example.px_app_api.rpc.AuthService;
import com.example.px_app_service.domain.User;
import com.example.px_app_service.mapper.UserMapper;
import com.example.px_app_service.util.JwtUtil;
import com.example.px_common.enums.BizCode;
import com.example.px_common.response.RpcResponse;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.HashMap;

@DubboService
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private LoginResponse loginResponse;

    /**
     * 登录
     *
     * @param loginRequestDto
     * @return
     */
    @Override
    public RpcResponse login(@RequestBody LoginRequest loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);

        //用户名唯一
        if (user == null) {
//            throw new BizException(BizCode.USER_NOT_EXIST);
            return RpcResponse.error(BizCode.USER_NOT_EXIST);
        }

        //密码判断
        Boolean isMatch = passwordEncoder.matches(password, user.getPassword());
        if (!isMatch) {
//            throw new BizException(BizCode.AUTH_INVALID_CREDENTIALS);
            return RpcResponse.error(BizCode.AUTH_INVALID_CREDENTIALS);
        }

        //生成token
        String token = jwtUtil.generateToken(user, "app");

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("username", user.getUsername());
        map.put("token", token);

        return RpcResponse.success(map);
    }

    @Override
    public RpcResponse register(@RequestBody RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User existUser = userMapper.selectOne(queryWrapper);
        if (existUser != null) {
            return RpcResponse.error(BizCode.USER_EXIST);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setStatus(1);
        user.setRegisterTime(Instant.now());
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        userMapper.insert(user);

        String token = jwtUtil.generateToken(user, "app");
        LoginResponse registerResponse = LoginResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();

        return RpcResponse.success(registerResponse);
    }
}
