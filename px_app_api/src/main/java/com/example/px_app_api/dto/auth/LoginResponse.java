package com.example.px_app_api.dto.auth;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse implements Serializable {

    private Long userId;

    private String username;

    private String token;
}
