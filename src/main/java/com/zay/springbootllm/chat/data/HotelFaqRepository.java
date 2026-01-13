package com.zay.springbootllm.chat.data;

import com.zay.springbootllm.chat.hotel.HotelFaqIntent;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class HotelFaqRepository {

    private final Map<String, Map<HotelFaqIntent, String>> data = new HashMap<>();

    public HotelFaqRepository() {

        Map<HotelFaqIntent, String> bangkokHotel = new EnumMap<>(HotelFaqIntent.class);
        bangkokHotel.put(HotelFaqIntent.CHECK_IN, "Check-in starts at 2:00 PM.");
        bangkokHotel.put(HotelFaqIntent.CHECK_OUT, "Check-out is until 12:00 PM.");
        bangkokHotel.put(HotelFaqIntent.BREAKFAST, "Breakfast is served from 6:30 AM to 10:00 AM.");
        bangkokHotel.put(HotelFaqIntent.WIFI, "Free Wi-Fi is available in delux rooms.");
        bangkokHotel.put(HotelFaqIntent.POOL, "The pool is open from 7:00 AM to 9:00 PM.");

        Map<HotelFaqIntent, String> chiangMaiHotel = new EnumMap<>(HotelFaqIntent.class);
        chiangMaiHotel.put(HotelFaqIntent.CHECK_IN, "Check-in starts at 3:00 PM.");
        chiangMaiHotel.put(HotelFaqIntent.CHECK_OUT, "Check-out is until 11:00 AM.");
        chiangMaiHotel.put(HotelFaqIntent.BREAKFAST, "Breakfast is served from 7:00 AM to 10:30 AM.");
        chiangMaiHotel.put(HotelFaqIntent.WIFI, "Complimentary Wi-Fi is available throughout the hotel.");
        chiangMaiHotel.put(HotelFaqIntent.POOL, "The pool is open from 8:00 AM to 8:00 PM.");

        data.put("bangkok-hotel", bangkokHotel);
        data.put("pattaya-hotel", chiangMaiHotel);
    }

    public Map<HotelFaqIntent, String> findAnswer(String hotelId, List<HotelFaqIntent> intents) {
        return intents.stream()
                .filter(i -> data.get(hotelId).containsKey(i))
                .collect(Collectors.toMap(
                        i -> i,
                        i -> data.get(hotelId).get(i)
                ));
    }
}
