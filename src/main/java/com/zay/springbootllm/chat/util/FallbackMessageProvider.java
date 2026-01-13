package com.zay.springbootllm.chat.util;

public final class FallbackMessageProvider {
    private FallbackMessageProvider(){}

    public static String fallbackMessage(UserLanguage language){
        return switch (language) {
            case MM -> "ကျေးဇူးပြု၍ ခဏနားပြီး ပြန်မေးပါ။";
            case TH -> "กรุณาลองใหม่อีกครั้งในอีกสักครู่ค่ะ";
            case EN -> "Please try again in a moment.";
        };
    }
}
