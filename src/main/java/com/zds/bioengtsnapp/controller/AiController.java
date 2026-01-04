package com.zds.bioengtsnapp.controller;

import com.zds.bioengtsnapp.service.DeepSeekService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AiController {

    private final DeepSeekService deepSeekService;

    public AiController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/ai/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        String response = deepSeekService.chat(message);
        return Map.of("response", response);
    }
}
