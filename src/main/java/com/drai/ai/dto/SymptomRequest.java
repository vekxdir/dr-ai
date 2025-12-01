package com.drai.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SymptomRequest {
    private Integer age;
    private String sex;
    private List<String> symptoms;
}
