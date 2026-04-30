package com.example.px_app_api.dto.frontend.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "前台用户登录响应")
public class LoginResponse implements Serializable {

    @Schema(description = "用户 ID", example = "11")
    private Long userId;

    @Schema(description = "用户名", example = "jackchen12")
    private String username;

    @Schema(description = "前台 JWT token", example = "eyJhbGciOiJIUzI1NiJ9.xxx.xxx")
    private String token;
}
