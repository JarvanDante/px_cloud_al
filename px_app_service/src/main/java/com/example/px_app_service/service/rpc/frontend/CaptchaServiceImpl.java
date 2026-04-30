package com.example.px_app_service.service.rpc.frontend;

import com.example.px_app_api.dto.frontend.app.CaptchaRequest;
import com.example.px_app_api.dto.frontend.app.CaptchaResponse;
import com.example.px_app_api.rpc.frontend.CaptchaService;
import com.example.px_common.enums.BizCode;
import com.example.px_common.response.RpcResponse;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.time.Duration;

@DubboService
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int CAPTCHA_EXPIRE_SECONDS = 300;
    private static final String DEFAULT_SITE_CODE = "default";
    private static final String CONTENT_TYPE = "image/png";

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public RpcResponse<CaptchaResponse> captcha(CaptchaRequest captchaRequest) {
        if (captchaRequest == null || captchaRequest.getTime() == null) {
            return RpcResponse.error(BizCode.CAPTCHA_PARAM_ERROR);
        }

        String code = String.format("%04d", RANDOM.nextInt(10_000));
        byte[] imageData;
        try {
            imageData = generateCaptchaImage(code);
        } catch (Exception e) {
            return RpcResponse.error(BizCode.CAPTCHA_GENERATE_ERROR);
        }

        if (imageData.length == 0) {
            return RpcResponse.error(BizCode.CAPTCHA_GENERATE_ERROR);
        }

        cacheCaptcha(captchaRequest, code);

        CaptchaResponse response = CaptchaResponse.builder()
                .image(imageData)
                .contentType(CONTENT_TYPE)
                .build();
        return RpcResponse.success(response);
    }

    private void cacheCaptcha(CaptchaRequest request, String code) {
        String siteCode = hasText(request.getSiteCode()) ? request.getSiteCode().trim() : DEFAULT_SITE_CODE;
        String clientIp = hasText(request.getClientIp()) ? request.getClientIp().trim() : "unknown";
        String cacheKey = siteCode + ":captcha:" + clientIp + ":" + request.getTime();

        try {
            stringRedisTemplate.opsForValue().set(cacheKey, code, Duration.ofSeconds(CAPTCHA_EXPIRE_SECONDS));
        } catch (Exception ignored) {
            // 验证码图片仍可返回；Redis 异常交给后续登录校验失败兜底。
        }
    }

    private byte[] generateCaptchaImage(String code) throws Exception {
        int width = 120;
        int height = 44;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        try {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(new Color(246, 248, 252));
            graphics.fillRect(0, 0, width, height);

            drawNoise(graphics, width, height);
            drawCode(graphics, code);
        } finally {
            graphics.dispose();
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(image, "png", output);
        return output.toByteArray();
    }

    private void drawCode(Graphics2D graphics, String code) {
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        for (int i = 0; i < code.length(); i++) {
            graphics.setColor(new Color(30 + RANDOM.nextInt(80), 60 + RANDOM.nextInt(80), 110 + RANDOM.nextInt(80)));
            int x = 18 + i * 24;
            int y = 31 + RANDOM.nextInt(5);
            int angle = RANDOM.nextInt(21) - 10;
            graphics.rotate(Math.toRadians(angle), x, y);
            graphics.drawString(String.valueOf(code.charAt(i)), x, y);
            graphics.rotate(Math.toRadians(-angle), x, y);
        }
    }

    private void drawNoise(Graphics2D graphics, int width, int height) {
        for (int i = 0; i < 12; i++) {
            graphics.setColor(new Color(150 + RANDOM.nextInt(80), 150 + RANDOM.nextInt(80), 150 + RANDOM.nextInt(80)));
            int x1 = RANDOM.nextInt(width);
            int y1 = RANDOM.nextInt(height);
            int x2 = RANDOM.nextInt(width);
            int y2 = RANDOM.nextInt(height);
            graphics.drawLine(x1, y1, x2, y2);
        }

        for (int i = 0; i < 60; i++) {
            graphics.setColor(new Color(120 + RANDOM.nextInt(100), 120 + RANDOM.nextInt(100), 120 + RANDOM.nextInt(100)));
            graphics.fillOval(RANDOM.nextInt(width), RANDOM.nextInt(height), 2, 2);
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
