package com.zay.springbootllm.chat.web;

import com.zay.springbootllm.chat.util.AiChatResponse;
import com.zay.springbootllm.chat.service.GeminiService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/ai")
@Controller
public class GeminiAiController {

    private final GeminiService geminiService;

    public GeminiAiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping(
            value = "/question",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AiChatResponse> askQuestion(
            @RequestBody String question,
            @RequestParam(value = "X-Conversation-Id", required = false) String conversationId,
            @RequestParam(value = "X-Hotel-Id", required = false) String hotelId
    ) {
        return ResponseEntity.ok(geminiService.getAnswer(question, conversationId, hotelId));
    }
}
