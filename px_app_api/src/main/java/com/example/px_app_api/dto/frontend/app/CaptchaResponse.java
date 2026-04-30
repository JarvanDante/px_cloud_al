package com.example.px_app_api.dto.frontend.app;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "验证码响应")
public class CaptchaResponse implements Serializable {
    @Schema(description = "验证码 PNG 图片二进制数据")
    private byte[] image;

    @Schema(description = "图片内容类型", example = "image/png")
    private String contentType;
}
