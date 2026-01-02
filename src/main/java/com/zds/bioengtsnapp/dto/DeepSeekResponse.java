package com.zds.bioengtsnapp.dto;

import lombok.Data;
import java.util.List;

@Data
public class DeepSeekResponse {
    private String id;
    private List<Choice> choices;
    
    @Data
    public static class Choice {
        private int index;
        private DeepSeekRequest.Message message;
        private String finish_reason;
    }
}
