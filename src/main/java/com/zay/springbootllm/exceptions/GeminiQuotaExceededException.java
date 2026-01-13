package com.zay.springbootllm.exceptions;

public class GeminiQuotaExceededException extends RuntimeException {

    public GeminiQuotaExceededException(String message) {
        super(message);
    }
}
