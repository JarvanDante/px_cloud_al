package com.example.px_app_api.dto.frontend.user;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {
    private Long id;
    private Integer siteId;
    private Integer gradeId;
    private Integer agentId;
    private Integer inviterId;
    private String username;
    private String mobile;
    private String email;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
}
