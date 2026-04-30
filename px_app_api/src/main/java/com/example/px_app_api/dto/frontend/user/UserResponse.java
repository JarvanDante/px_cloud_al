package com.example.px_app_api.dto.frontend.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "前台用户信息响应")
public class UserResponse implements Serializable {
    @Schema(description = "用户 ID", example = "11")
    private Long id;

    @Schema(description = "站点 ID", example = "1")
    private Integer siteId;

    @Schema(description = "等级 ID", example = "1")
    private Integer gradeId;

    @Schema(description = "代理 ID", example = "0")
    private Integer agentId;

    @Schema(description = "邀请人 ID", example = "0")
    private Integer inviterId;

    @Schema(description = "用户名", example = "jackchen12")
    private String username;

    @Schema(description = "手机号", example = "13800138000")
    private String mobile;

    @Schema(description = "邮箱", example = "user@example.com")
    private String email;

    @Schema(description = "最后登录时间", example = "2026-04-30 21:45:10")
    private LocalDateTime lastLoginTime;

    @Schema(description = "最后登录 IP", example = "172.19.0.5")
    private String lastLoginIp;
}
