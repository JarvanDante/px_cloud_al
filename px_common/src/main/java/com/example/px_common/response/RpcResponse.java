package com.example.px_common.response;

import com.example.px_common.enums.BizCode;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        return new RpcResponse<>(0, "success", data);
    }

    public static <T> RpcResponse<T> error(BizCode code) {
        return new RpcResponse<>(code.getCode(), code.getMessage(), null);
    }

    public boolean isSuccess() {
        return code == 0;
    }
}