package com.drai.ai.service.impl;

import com.drai.ai.client.GeminiClient;
import com.drai.ai.dto.AnalysisResponse;
import com.drai.ai.dto.ConditionItem;
import com.drai.ai.dto.SymptomRequest;
import com.drai.ai.service.SymptomCheckerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SymptomCheckerServiceImpl implements SymptomCheckerService {

    private final GeminiClient geminiClient;
    private final ObjectMapper objectMapper;

    @Override
    public AnalysisResponse analyze(SymptomRequest req) {
        if (req.getSymptoms() == null || req.getSymptoms().isEmpty()) {
            return AnalysisResponse.builder()
                    .conditions(Collections.emptyList())
                    .build();
        }

        String symptoms = String.join(", ", req.getSymptoms());
        String jsonResponse = geminiClient.askSymptomCheck(symptoms);
        System.out.println("DEBUG: Raw Gemini Response: " + jsonResponse);

        try {
            // Clean up markdown code blocks if present (Gemini sometimes adds them despite
            // instructions)
            if (jsonResponse.startsWith("```json")) {
                jsonResponse = jsonResponse.substring(7);
            }
            if (jsonResponse.startsWith("```")) {
                jsonResponse = jsonResponse.substring(3);
            }
            if (jsonResponse.endsWith("```")) {
                jsonResponse = jsonResponse.substring(0, jsonResponse.length() - 3);
            }

            System.out.println("DEBUG: Cleaned JSON: " + jsonResponse);

            List<ConditionItem> conditions = objectMapper.readValue(jsonResponse.trim(),
                    new TypeReference<List<ConditionItem>>() {
                    });
            System.out.println("DEBUG: Parsed Conditions: " + conditions);

            return AnalysisResponse.builder()
                    .conditions(conditions)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            // Fallback error condition
            return AnalysisResponse.builder()
                    .conditions(List.of(ConditionItem.builder()
                            .name("Error Analyzing Symptoms")
                            .overview("We could not process the AI response. Please try again.")
                            .treatment("Consult a doctor directly.")
                            .matchScore(0.0)
                            .build()))
                    .build();
        }
    }
}
