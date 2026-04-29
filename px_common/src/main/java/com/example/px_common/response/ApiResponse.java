package com.example.px_common.response;

import com.example.px_common.enums.BizCode;


public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    private long timestamp;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // ================= 成功 =================
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(BizCode.SUCCESS.getCode(), BizCode.SUCCESS.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(BizCode.SUCCESS.getCode(), BizCode.SUCCESS.getMessage(), data);
    }

    // ================= 失败 =================
    public static <T> ApiResponse<T> error(BizCode bizCode) {
        return new ApiResponse<>(bizCode.getCode(), bizCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // ================= getter/setter =================

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
