package com.zds.bioengtsnapp.service;

import com.zds.bioengtsnapp.dto.DeepSeekRequest;
import com.zds.bioengtsnapp.dto.DeepSeekResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeepSeekService {

    private final WebClient deepSeekWebClient;

    private static final String SYSTEM_PROMPT = 
        "You are an AI assistant for Imperial College London. " +
        "You must strictly follow these rules:\n" +
        "1. All answers must be in English.\n" +
        "2. You can ONLY answer questions related to Imperial College London.\n" +
        "3. You must verify information from official sources before answering.\n" +
        "4. Your responses must be positive, helpful, and professional.\n" +
        "5. Do NOT discuss sensitive political topics, national politics, pornography, or vulgar content.\n" +
        "6. If a question violates these rules, politely refuse to answer and explain why.";

    @Autowired
    public DeepSeekService(WebClient deepSeekWebClient) {
        this.deepSeekWebClient = deepSeekWebClient;
    }

    public Mono<String> chat(String userMessage) {
        List<DeepSeekRequest.Message> messages = new ArrayList<>();
        messages.add(new DeepSeekRequest.Message("system", SYSTEM_PROMPT));
        messages.add(new DeepSeekRequest.Message("user", userMessage));

        DeepSeekRequest request = DeepSeekRequest.builder()
                .model("deepseek-chat")
                .messages(messages)
                .temperature(0.7)
                .max_tokens(500)
                .stream(false)
                .build();

        return deepSeekWebClient.post()
                .uri("/chat/completions")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DeepSeekResponse.class)
                .map(response -> {
                    if (response.getChoices() != null && !response.getChoices().isEmpty()) {
                        return response.getChoices().get(0).getMessage().getContent();
                    }
                    return "Sorry, I couldn't generate a response.";
                })
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just("Error communicating with AI service: " + e.getMessage());
                });
    }
}
