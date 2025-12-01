package com.drai.ai.service.impl;

import com.drai.ai.client.GeminiClient;
import com.drai.ai.dto.DietRequest;
import com.drai.ai.dto.DietResponse;
import com.drai.ai.service.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DietServiceImpl implements DietService {

    private final GeminiClient geminiClient;

    @Override
    public DietResponse generateDiet(DietRequest req) {
        String prompt = String.format(
                "Create a 1-day diet plan for a %d year old %s, %skg, %scm tall person who wants to %s. " +
                        "Dietary Preference: %s. " +
                        "Format the response in clean Markdown. " +
                        "Include Breakfast, Lunch, Snack, and Dinner. " +
                        "Add approximate calories for each meal.",
                req.getAge(), req.getGender(), req.getWeight(), req.getHeight(), req.getGoal(), req.getPreference());

        String response = geminiClient.ask(prompt);

        return DietResponse.builder()
                .markdownContent(response)
                .build();
    }
}
