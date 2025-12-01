package com.drai.ai.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SymptomResponse {
    private List<ConditionItem> conditions;
}
