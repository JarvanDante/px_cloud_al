package com.example.px_app_api.dto.user;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private Integer siteId;
    private Integer gradeId;
    private Integer agentId;
    private Integer inviterId;
    private String username;
    private String mobile;
    private String email;
    private Instant lastLoginTime;
    private String lastLoginIp;
}
