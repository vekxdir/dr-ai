package com.drai.ai.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponseDto {
    private String answer;   // MATCH frontend
    private String model;
}
