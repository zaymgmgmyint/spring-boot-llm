package com.zay.springbootllm.chat.data;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

@Component
public class RoomPriceRepository {

    // Prices in Thai Baht (THB)
    private final Map<String, Integer> roomPrices = Map.of(
            "standard", 2800,
            "deluxe", 4200,
            "suite", 7000,
            "family", 5500
    );

    private static final Locale THAI_LOCALE = new Locale("th", "TH");

    public String getFormattedPrice(String question) {
        String roomType = extractRoomType(question);
        Integer price = roomPrices.get(roomType);

        if (price == null) {
            return "Sorry, we do not have pricing information for that room type.";
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(THAI_LOCALE);
        String formattedPrice = currencyFormat.format(price);

        return String.format(
                "The %s room costs %s per night.",
                capitalize(roomType),
                formattedPrice
        );
    }



    private String extractRoomType(String question) {
        String q = question.toLowerCase();
        return roomPrices.keySet().stream()
                .filter(q::contains)
                .findFirst()
                .orElse("");
    }

    private String capitalize(String value) {
        if (value.isEmpty()) return value;
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }
}
