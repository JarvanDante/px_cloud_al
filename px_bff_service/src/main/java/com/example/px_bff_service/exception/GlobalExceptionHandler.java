package com.example.px_bff_service.exception;

import com.example.px_common.exception.BizException;
import com.example.px_common.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ApiResponse<?> handleBizException(BizException e) {
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(org.apache.dubbo.rpc.RpcException.class)
    public ApiResponse<?> handleRpcException(org.apache.dubbo.rpc.RpcException e) {
        Throwable cause = e.getCause();

        if (cause instanceof BizException bizException) {
            return ApiResponse.error(bizException.getCode(), bizException.getMessage());
        }

        return ApiResponse.error(5001, "服务调用异常");
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        e.printStackTrace();
        System.out.println("异常类型 = " + e.getClass().getName());
        System.out.println("异常原因 = " + (e.getCause() == null ? "null" : e.getCause().getClass().getName()));
        return ApiResponse.error(5000, "系统异常");
    }
}