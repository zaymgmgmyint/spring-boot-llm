package com.zay.springbootllm.chat.hotel;

import com.zay.springbootllm.chat.data.HotelFaqRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class HotelFaqTool {

    private final HotelFaqRepository repository;

    public HotelFaqTool(HotelFaqRepository repository) {
        this.repository = repository;
    }

    @Tool(
            name = "hotel_faq",
            description = """
                    Use this tool to answer hotel-specific questions.
                    It supports the following intents:
                    CHECK_IN, CHECK_OUT, BREAKFAST, WIFI, PARKING, POOL.
                    
                    This tool ALWAYS has data for the provided hotelId.
                    """
    )
    public Map<HotelFaqIntent, String> hotelFaq(
            @ToolParam(description = "Hotel ID, for example: bangkok-hotel or pattaya-hotel")
            String hotelId,

            @ToolParam(description = "FAQ intent")
            List<HotelFaqIntent> intents
    ) {
        return repository.findAnswer(hotelId, intents);
    }
}
