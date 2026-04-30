package com.example.px_app_service.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Locale;

public class Google2FAUtil {
    private static final int TIME_STEP_SECONDS = 30;
    private static final int CODE_DIGITS = 6;
    private static final int WINDOW_SIZE = 1;
    private static final String HMAC_ALGORITHM = "HmacSHA1";

    private Google2FAUtil() {
    }

    public static boolean isTOTPCodeValid(String code, String secret) {
        if (code == null || secret == null) {
            return false;
        }

        Instant now = Instant.now();
        for (int i = -WINDOW_SIZE; i <= WINDOW_SIZE; i++) {
            String expectedCode = generateTOTPCode(secret, now.plusSeconds((long) i * TIME_STEP_SECONDS));
            if (constantTimeEquals(expectedCode, code.trim())) {
                return true;
            }
        }
        return false;
    }

    public static String generateTOTPCode(String secret) {
        return generateTOTPCode(secret, Instant.now());
    }

    private static String generateTOTPCode(String secret, Instant time) {
        try {
            byte[] key = decodeBase32(secret);
            long counter = time.getEpochSecond() / TIME_STEP_SECONDS;
            byte[] counterBytes = ByteBuffer.allocate(Long.BYTES).putLong(counter).array();

            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(key, HMAC_ALGORITHM));
            byte[] hash = mac.doFinal(counterBytes);

            int offset = hash[hash.length - 1] & 0x0f;
            int binaryCode = ((hash[offset] & 0x7f) << 24)
                    | ((hash[offset + 1] & 0xff) << 16)
                    | ((hash[offset + 2] & 0xff) << 8)
                    | (hash[offset + 3] & 0xff);

            int otp = binaryCode % (int) Math.pow(10, CODE_DIGITS);
            return String.format(Locale.ROOT, "%0" + CODE_DIGITS + "d", otp);
        } catch (Exception e) {
            return "";
        }
    }

    private static byte[] decodeBase32(String secret) {
        String normalizedSecret = secret.trim()
                .replace(" ", "")
                .replace("=", "")
                .toUpperCase(Locale.ROOT);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int buffer = 0;
        int bitsLeft = 0;

        for (int i = 0; i < normalizedSecret.length(); i++) {
            int value = base32Value(normalizedSecret.charAt(i));
            if (value < 0) {
                throw new IllegalArgumentException("Invalid Base32 secret");
            }

            buffer = (buffer << 5) | value;
            bitsLeft += 5;

            if (bitsLeft >= 8) {
                output.write((buffer >> (bitsLeft - 8)) & 0xff);
                bitsLeft -= 8;
            }
        }

        return output.toByteArray();
    }

    private static int base32Value(char c) {
        if (c >= 'A' && c <= 'Z') {
            return c - 'A';
        }
        if (c >= '2' && c <= '7') {
            return c - '2' + 26;
        }
        return -1;
    }

    private static boolean constantTimeEquals(String expectedCode, String actualCode) {
        if (expectedCode == null || actualCode == null || expectedCode.length() != actualCode.length()) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < expectedCode.length(); i++) {
            result |= expectedCode.charAt(i) ^ actualCode.charAt(i);
        }
        return result == 0;
    }
}
