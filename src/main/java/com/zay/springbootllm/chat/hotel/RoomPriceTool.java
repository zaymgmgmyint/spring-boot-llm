package com.zay.springbootllm.chat.hotel;

import com.zay.springbootllm.chat.data.RoomPriceRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class RoomPriceTool {

    private final RoomPriceRepository roomPriceRepository;

    public RoomPriceTool(RoomPriceRepository roomPriceRepository) {
        this.roomPriceRepository = roomPriceRepository;
    }

    @Tool(
            name = "room_price",
            description = """
                    Use this tool to get room price information.
                    Supported room types: standard, deluxe, suite, family.
                    Prices are returned in Thai Baht.
                    """
    )
    public String getRoomPrice(String question){
        return roomPriceRepository.getFormattedPrice(question);
    }

}
