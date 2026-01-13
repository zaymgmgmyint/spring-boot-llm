package com.zay.springbootllm.config;

import com.zay.springbootllm.chat.util.AiChatResponse;
import com.zay.springbootllm.exceptions.GeminiQuotaExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAiExceptionHandler {

    @ExceptionHandler(GeminiQuotaExceededException.class)
    public ResponseEntity<AiChatResponse> handleQuotaExceededException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(
                        new AiChatResponse("The assistant has reached its usage limit for now. Please try again later.",
                                null)
                );
    }
}
