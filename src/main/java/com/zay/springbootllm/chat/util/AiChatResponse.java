package com.zay.springbootllm.chat.util;

public record AiChatResponse(
        String answer,
        TokenUsage usage
) {
    public record TokenUsage(
            int promptTokens,
            int completionTokens,
            int totalTokens
    ) {}
}
