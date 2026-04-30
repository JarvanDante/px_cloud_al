package com.example.px_app_api.dto.backend.auth;

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
@Schema(description = "后台管理员登录响应")
public class LoginResponse implements Serializable {
    @Schema(description = "后台 JWT token", example = "eyJhbGciOiJIUzI1NiJ9.xxx.xxx")
    private String token;
}
