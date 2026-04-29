package com.example.px_common.exception;

import com.example.px_common.enums.BizCode;

public class BizException extends RuntimeException {
    private final int code;
    private final String message;

    public BizException(BizCode bizCode) {
        super(bizCode.getMessage());
        this.code = bizCode.getCode();
        this.message = bizCode.getMessage();
    }

    public BizException(BizCode bizCode, String message) {
        super(message);
        this.code = bizCode.getCode();
        this.message = message;
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
