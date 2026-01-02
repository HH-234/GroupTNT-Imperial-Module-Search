package com.zds.bioengtsnapp.controller;

import com.zds.bioengtsnapp.service.DeepSeekService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final DeepSeekService deepSeekService;

    public AiController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/chat")
    public Mono<Map<String, String>> chat(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        return deepSeekService.chat(message)
                .map(response -> Map.of("response", response));
    }
}
