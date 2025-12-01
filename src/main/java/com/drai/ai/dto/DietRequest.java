package com.drai.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietRequest {
    private Integer age;
    private Double weight; // in kg
    private Double height; // in cm
    private String goal; // e.g., "Lose Weight", "Gain Muscle"
    private String preference; // e.g., "Vegetarian", "Non-Vegetarian"
    private String gender; // "Male", "Female"
}
