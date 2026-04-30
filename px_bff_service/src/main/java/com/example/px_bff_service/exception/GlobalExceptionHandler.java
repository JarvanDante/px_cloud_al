package com.example.px_bff_service.exception;

import com.example.px_common.enums.BizCode;
import com.example.px_common.exception.BizException;
import com.example.px_common.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return ApiResponse.error(405, "请求方法不支持");
    }

    // 业务异常
    @ExceptionHandler(BizException.class)
    public ApiResponse<?> handleBizException(BizException e) {
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    // @RequestBody 校验
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .findFirst()
                .orElse("参数错误");

        return ApiResponse.error(400, errorMsg);
    }


    // 缺少必填请求参数，例如 ?time=xxx 未传
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse<?> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        return ApiResponse.error(BizCode.PARAM_MISSING.getCode(), BizCode.PARAM_MISSING.getMessage() + "：" + e.getParameterName());
    }

    // @RequestParam / @PathVariable 校验
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<?> handleConstraintViolation(ConstraintViolationException ex) {
        String errorMsg = ex.getConstraintViolations()
                .iterator()
                .next()
                .getMessage();

        return ApiResponse.error(400, errorMsg);
    }

    // 请求体错误
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<?> handleBodyError() {
        return ApiResponse.error(400, "请求参数格式错误");
    }

    @ExceptionHandler(org.apache.dubbo.rpc.RpcException.class)
    public ApiResponse<?> handleRpcException(org.apache.dubbo.rpc.RpcException e) {
        Throwable cause = e.getCause();

        if (cause instanceof BizException bizException) {
            return ApiResponse.error(bizException.getCode(), bizException.getMessage());
        }

        return ApiResponse.error(5001, "服务调用异常");
    }

    // 兜底异常
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        e.printStackTrace();
        System.out.println("异常类型 = " + e.getClass().getName());
        System.out.println("异常原因 = " + (e.getCause() == null ? "null" : e.getCause().getClass().getName()));
        return ApiResponse.error(5000, "系统异常");
    }
}