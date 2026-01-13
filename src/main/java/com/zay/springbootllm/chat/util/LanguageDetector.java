package com.zay.springbootllm.chat.util;

public class LanguageDetector {
    private LanguageDetector() {}

    public static UserLanguage detectLanguage(String text) {
        if(text == null || text.isBlank()){
            return UserLanguage.TH;
        }

        // Burmese (Myanmar) Unicode range: U+1000 to U+109F
        if(text.matches(".*[\\u1000-\\u109F].*")) {
            return UserLanguage.MM;
        }

        // Thai Unicode range: U+0E00 to U+0E7F
        if(text.matches(".*[\\u0E00-\\u0E7F].*")) {
            return UserLanguage.TH;
        }

        // English as default
        return UserLanguage.EN;
    }
}
