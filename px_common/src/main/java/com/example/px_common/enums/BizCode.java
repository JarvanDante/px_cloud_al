package com.example.px_common.enums;

public enum BizCode {

    SUCCESS(0, "success"),

    // 1xxx: 认证(auth)
    //账号或密码错误
    AUTH_INVALID_CREDENTIALS(1001, "账号或密码错误"),

    // 2xxx: 用户(user)
    // 用户相关
    USER_NOT_EXIST(2001, "用户不存在"),
    USER_PASSWORD_ERROR(2002, "密码错误"),
    USER_DISABLED(2003, "用户已被禁用"),
    USER_EXIST(2004, "用户已存在"),

    // 3xxx: 管理员(admin)
    // 管理员相关
    ADMIN_NOT_EXIST(3001, "管理员不存在"),
    ADMIN_PASSWORD_ERROR(3002, "密码错误"),
    ADMIN_INPUT_CODE(3003, "请输入动态验证码"),
    ADMIN_NOT_CONFIG_GOOGLE_2FA(3004, "二次验证未正确配置"),
    ADMIN_GOOGLE_CODE_ERROR(3005, "动态验证码错误"),
    // 通用
    SYSTEM_ERROR(5000, "系统异常");

    private final int code;
    private final String message;

    BizCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}