package com.example.px_bff_service.controller.frontend;

import com.example.px_app_api.dto.frontend.user.UserResponse;
import com.example.px_app_api.rpc.frontend.UserService;
import com.example.px_common.response.ApiResponse;
import com.example.px_common.response.RpcResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "前台用户", description = "前台用户资料相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/frontend/user")
public class FrontendUserController {

    @DubboReference
    private final UserService userService;

    @Operation(summary = "获取当前用户信息", description = "根据网关解析出的用户 ID 查询当前登录用户资料。")
    @GetMapping("/user-info")
    public ApiResponse<UserResponse> userInfo(
            @Parameter(description = "网关从 JWT 中解析出的用户 ID", example = "11", required = true)
            @RequestHeader("X-User-Id") Long userId) {
        RpcResponse<UserResponse> rpc = userService.userInfo(userId);
        if (!rpc.isSuccess()) {
            return ApiResponse.error(rpc.getCode(), rpc.getMessage());
        }
        return ApiResponse.success(rpc.getData());
    }
}
