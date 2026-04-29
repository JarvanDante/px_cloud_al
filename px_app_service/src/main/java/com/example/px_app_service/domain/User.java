package com.example.px_app_service.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer siteId = 1;

    private Integer gradeId = 1;

    private Integer agentId = 0;

    private Integer inviterId = 0;

    private String username;

    private String password;

    private String payPassword;
    private Integer status;
    private String registerIp;
    private Instant registerTime;
    private String registerUrl;
    private String lastLoginIp;
    private Instant lastLoginTime;
    private String lastLoginAddress;
    private String mobile;
    private String email;
    private LocalDate birthday;
    private Integer isOnline;
    private String remark = "";
    private Instant createdAt;
    private Instant updatedAt;
    private Integer payTimes = 0;
    private Integer hasPass;
}
