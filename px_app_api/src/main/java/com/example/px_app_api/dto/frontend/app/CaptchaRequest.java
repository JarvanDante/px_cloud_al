package com.example.px_app_api.dto.frontend.app;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Schema(description = "验证码请求")
public class CaptchaRequest implements Serializable {
    @Schema(description = "验证码请求时间戳，用于组成缓存 key", example = "1777576202244", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请求时间不能为空")
    private Long time;

    @Schema(description = "客户端 IP，由 BFF 写入", example = "127.0.0.1", accessMode = Schema.AccessMode.READ_ONLY)
    private String clientIp;

    @Schema(description = "站点代码，由 BFF 写入，默认 default", example = "default", accessMode = Schema.AccessMode.READ_ONLY)
    private String siteCode;
}
