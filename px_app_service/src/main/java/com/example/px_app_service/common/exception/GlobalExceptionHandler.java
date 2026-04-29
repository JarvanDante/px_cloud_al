package com.example.px_app_service.common.exception;

import com.example.px_common.exception.BizException;
import com.example.px_common.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    // 兜底异常
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        return ApiResponse.error(5000, "系统异常");
    }
}