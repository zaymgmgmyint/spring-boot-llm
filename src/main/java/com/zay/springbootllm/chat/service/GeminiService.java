package com.zay.springbootllm.chat.service;

import com.zay.springbootllm.chat.hotel.HotelFaqTool;
import com.zay.springbootllm.chat.hotel.RoomPriceTool;
import com.zay.springbootllm.chat.util.AiChatResponse;
import com.zay.springbootllm.chat.util.FallbackMessageProvider;
import com.zay.springbootllm.chat.util.LanguageDetector;
import com.zay.springbootllm.chat.util.UserLanguage;
import com.zay.springbootllm.exceptions.GeminiQuotaExceededException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiService {

    private final ChatClient chatClient;
    private final HotelFaqTool hotelFaqTool;
    private final RoomPriceTool roomPriceTool;

    private static final String SYSTEM_PROMPT = """
            You are a professional hotel front desk and concierge assistant.
            You are serving the hotel with ID: %s.
            All hotel-related answers MUST follow this hotel's policies.
            
            Language rules:
            - Detect the user's language automatically.
            - Supported languages: English, Thai, Burmese (Myanmar).
            - Always respond in the same language.
            - If unclear, default to English.
            
            Tone rules:
            - Polite, concise, friendly.
            - One or two short sentences.
            - Plain text only.
            
            Scope rules:
            - You can answer questions in FOUR scopes:
              1. Hotel services and amenities
              2. Hotel-related prices provided by tools
              3. Local attractions and tips near the hotel
              4. General country-level travel tips, including cities and regions within the country
            
            Hotel rules:
            - If the question is about hotel services or amenities,
              classify it into one or more intents:
              CHECK_IN, CHECK_OUT, BREAKFAST, WIFI, PARKING, POOL.
            - If any hotel intent is detected,
              you MUST call the hotel_faq tool with the hotelId and all detected intents.
            - Never guess or invent hotel policies.
            
            Pricing rules:
            - If pricing information exists in a tool or database,
              you MUST use the tool and never guess.
            - If no pricing tool exists for the request,
              you MAY provide general price estimates based on common travel knowledge.
            - All estimated prices must be clearly described as approximate.
            - Never guarantee prices, availability, or packages.
            - Recommend confirming with official providers or the hotel.
            
            Local & country tips rules:
            - You SHOULD answer questions about local attractions, cities, islands, and regions within the country.
            - You MAY use general travel knowledge.
            - Keep suggestions high-level, descriptive, and experience-focused.
            - Focus on nature, culture, food, and common activities.
            - Do NOT provide prices, schedules, or guarantees.
            - Do NOT claim official authority.
            - Do NOT refuse unless the request is unsafe or unrelated to travel.

            Tool rules:
            - Use tools ONLY for authoritative hotel or pricing information.
            - Never mention tools or intents in your response.
            
            Your goal is to act like a real hotel front desk and concierge staff member,
            helpful but careful, informative but not authoritative.
            """;


    public AiChatResponse getAnswer(String question, String conversationId, String hotelId) {

        try {
            ChatResponse response = chatClient
                    .prompt()
                    .system(SYSTEM_PROMPT.formatted(hotelId))
                    .user(question)
                    .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                    .tools(
                            hotelFaqTool,
                            roomPriceTool
                    )
                    .call()
                    .chatResponse();


            String answer = response
                    .getResult()
                    .getOutput()
                    .getText();

            var usage = response.getMetadata().getUsage();
            Integer promptTokens = usage != null ? usage.getPromptTokens() : null;
            Integer completionTokens = usage != null ? usage.getCompletionTokens() : null;
            Integer totalTokens = usage != null ? usage.getTotalTokens() : null;

            log.info(
                    "Gemini token usage | prompt={} completion={} total={}",
                    promptTokens,
                    completionTokens,
                    totalTokens
            );

            return new AiChatResponse(
                    answer,
                    new AiChatResponse.TokenUsage(
                            promptTokens,
                            completionTokens,
                            totalTokens
                    )
            );
        } catch (Exception ex) {
            String message = ex.getMessage() != null ? ex.getMessage() : "Unknown error";
            UserLanguage lang = LanguageDetector.detectLanguage(question);

            if (message.contains("RESOURCE_EXHAUSTED")
                    || message.contains("429")
                    || message.contains("Quota")) {
                log.warn("Gemini quota exceeded: {}", message);

                return new AiChatResponse(
                        "Model quota exceeded. Please try again next day.",
                        null
                );
            }

            if (message.contains("Failed to generate content")) {
                log.warn("Temporary AI generation failure");
                return new AiChatResponse(
                        FallbackMessageProvider.fallbackMessage(lang),
                        null
                );
            }

            log.error("Unexpected Gemini error", ex);
            throw ex;
        }
    }
}