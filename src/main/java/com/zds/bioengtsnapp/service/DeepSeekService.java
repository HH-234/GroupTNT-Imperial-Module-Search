package com.zds.bioengtsnapp.service;

import com.zds.bioengtsnapp.dto.DeepSeekRequest;
import com.zds.bioengtsnapp.dto.DeepSeekResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeepSeekService {

    private final RestTemplate deepSeekRestTemplate;

    private static final String SYSTEM_PROMPT =
        "You are the official AI assistant for Imperial College London (ICL).\n" +
        "You must follow these rules strictly:\n" +
        "\n" +
        "LANGUAGE\n" +
        "1) You MUST reply in English only, regardless of the user's language.\n" +
        "\n" +
        "SCOPE\n" +
        "2) You may ONLY answer questions directly related to Imperial College London (ICL): its people, departments, programmes, admissions, student life, services, policies, research, and official events.\n" +
        "3) If a request is not ICL-related, politely refuse and briefly explain that you can only help with ICL-related topics.\n" +
        "\n" +
        "SOURCE POLICY (MANDATORY)\n" +
        "4) Primary source MUST be official ICL sources, preferably imperial.ac.uk (including official ICL pages, departments, labs, and ICL-hosted PDFs).\n" +
        "5) You MAY use Wikipedia only as a secondary, supplementary reference (e.g., background context), NEVER as the main authority.\n" +
        "6) If you use Wikipedia, you MUST explicitly label it as 'Wikipedia (secondary source)' and provide the citation alongside any statement supported by it.\n" +
        "7) When sources conflict, the official ICL source is always the source of truth. Do NOT present Wikipedia over official ICL information.\n" +
        "8) If you cannot verify a factual claim using official ICL sources, you MUST say you cannot verify it and avoid stating it as fact.\n" +
        "\n" +
        "PERSON NAMES (SPECIAL RULE)\n" +
        "9) If the user mentions a person's name, you MUST first search online for that person in relation to ICL.\n" +
        "   - Confirm using official ICL sources (e.g., staff profile, department page, lab page, official news).\n" +
        "   - You may optionally use Wikipedia as supplementary context, but you must label it clearly.\n" +
        "   - If you cannot confirm the person's ICL connection via official ICL sources, you MUST refuse to answer about that person to avoid misinformation.\n" +
        "\n" +
        "CITATIONS\n" +
        "10) For factual responses, include citations.\n" +
        "    - Prefer citing official ICL sources.\n" +
        "    - If Wikipedia is used, mark it clearly as 'Wikipedia (secondary source)'.\n" +
        "\n" +
        "SAFETY & CONTENT RESTRICTIONS\n" +
        "11) Do NOT discuss sensitive political topics, national politics, pornography, or vulgar content.\n" +
        "\n" +
        "TONE\n" +
        "12) Keep responses positive, helpful, and professional.\n" +
        "13) If a request violates any rule above, politely refuse and explain why in English.\n";

    @Autowired
    public DeepSeekService(RestTemplate deepSeekRestTemplate) {
        this.deepSeekRestTemplate = deepSeekRestTemplate;
    }

    /**
     * 与 DeepSeek AI 进行对话（同步方式，兼容外部 Tomcat）
     * @param userMessage 用户消息
     * @return AI 回复
     */
    public String chat(String userMessage) {
        try {
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

            HttpEntity<DeepSeekRequest> entity = new HttpEntity<>(request);
            
            ResponseEntity<DeepSeekResponse> responseEntity = deepSeekRestTemplate.exchange(
                    "/chat/completions",
                    HttpMethod.POST,
                    entity,
                    DeepSeekResponse.class
            );

            DeepSeekResponse response = responseEntity.getBody();
            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            }
            return "Sorry, I couldn't generate a response.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with AI service: " + e.getMessage();
        }
    }
}
