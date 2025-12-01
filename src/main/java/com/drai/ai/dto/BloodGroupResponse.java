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
public class BloodGroupResponse {
    private String recipientGroup;
    private List<String> compatibleDonors;
    private String message;
}
