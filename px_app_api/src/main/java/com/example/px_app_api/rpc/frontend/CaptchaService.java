package com.example.px_app_api.rpc.frontend;

import com.example.px_app_api.dto.frontend.app.CaptchaRequest;
import com.example.px_app_api.dto.frontend.app.CaptchaResponse;
import com.example.px_common.response.RpcResponse;

public interface CaptchaService {

    RpcResponse<CaptchaResponse> captcha(CaptchaRequest captchaRequest);
}
