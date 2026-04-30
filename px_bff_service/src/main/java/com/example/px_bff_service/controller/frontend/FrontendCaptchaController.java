package com.example.px_bff_service.controller.frontend;

import com.example.px_app_api.dto.frontend.app.CaptchaRequest;
import com.example.px_app_api.dto.frontend.app.CaptchaResponse;
import com.example.px_app_api.rpc.frontend.CaptchaService;
import com.example.px_common.response.RpcResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "前台验证码", description = "前台验证码相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/frontend/app")
public class FrontendCaptchaController {

    @DubboReference
    private final CaptchaService captchaService;

    @Operation(summary = "获取验证码图片", description = "生成 4 位数字验证码图片，并按 siteCode、客户端 IP、time 缓存 5 分钟。")
    @GetMapping(value = "/captcha", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> captcha(
            @Parameter(description = "验证码请求时间戳，用于组成缓存 key", example = "1777576202244", required = true)
            @Valid @RequestParam("time") Long time,
            HttpServletRequest request) {
        CaptchaRequest captchaRequest = new CaptchaRequest();
        captchaRequest.setTime(time);
        captchaRequest.setClientIp(getClientIp(request));
        captchaRequest.setSiteCode(getSiteCode(request));

        RpcResponse<CaptchaResponse> rpc = captchaService.captcha(captchaRequest);
        if (!rpc.isSuccess() || rpc.getData() == null || rpc.getData().getImage() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=captcha.png")
                .cacheControl(CacheControl.noStore())
                .contentType(MediaType.parseMediaType(rpc.getData().getContentType()))
                .body(rpc.getData().getImage());
    }

    private String getClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (hasText(forwardedFor)) {
            return forwardedFor.split(",")[0].trim();
        }

        String realIp = request.getHeader("X-Real-IP");
        if (hasText(realIp)) {
            return realIp.trim();
        }

        return request.getRemoteAddr();
    }

    private String getSiteCode(HttpServletRequest request) {
        String siteCode = request.getHeader("X-Site-Code");
        return hasText(siteCode) ? siteCode.trim() : "default";
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
