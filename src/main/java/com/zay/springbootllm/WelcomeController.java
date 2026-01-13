package com.zay.springbootllm;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @RequestMapping("/")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Welcome to Spring Boot LLM Application!");
    }
}
