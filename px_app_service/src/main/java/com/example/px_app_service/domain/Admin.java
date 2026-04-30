package com.example.px_app_service.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@TableName("admin")
public class Admin {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer siteId;
    @Size(max = 50)
    private String username;
    @Size(max = 50)
    private String nickname;
    @Size(max = 255)
    private String password;
    private Integer adminRoleId;
    private Integer status;
    private Integer switchGoogle2fa;
    @Size(max = 50)
    private String google2faSecret;
    private Integer transferAuditSound;
    @Size(max = 20)
    private String soundLoopTime;
    private Integer paymentSound;
    @Size(max = 45)
    private String lastLoginIp;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deleteAt;


}
