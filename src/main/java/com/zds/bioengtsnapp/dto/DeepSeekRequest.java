package com.zds.bioengtsnapp.dto;

import java.util.List;

public class DeepSeekRequest {
    private String model;
    private List<Message> messages;
    private Double temperature;
    private Integer max_tokens;
    private Boolean stream;

    public static DeepSeekRequest builder() {
        return new DeepSeekRequest();
    }

    public DeepSeekRequest model(String model) {
        this.model = model;
        return this;
    }

    public DeepSeekRequest messages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    public DeepSeekRequest temperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public DeepSeekRequest max_tokens(Integer max_tokens) {
        this.max_tokens = max_tokens;
        return this;
    }

    public DeepSeekRequest stream(Boolean stream) {
        this.stream = stream;
        return this;
    }

    public DeepSeekRequest build() {
        return this;
    }

    // Getters and Setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(Integer max_tokens) {
        this.max_tokens = max_tokens;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public static class Message {
        private String role;
        private String content;

        public Message() {
        }

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

