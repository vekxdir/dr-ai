package com.drai.ai.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GeminiClient {

    @Value("${app.gemini.api-key}")
    private String apiKey;

    @Value("${app.gemini.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    // 🔥 System Prompt (identity + rules)
    private String systemPrompt =
            "You are DR-AI, a medical assistant designed for safe and helpful replies. " +
                    "Founder: Vivek Yadav.\n\n" +
                    "RULES:\n" +
                    "1. You ONLY answer health and medical questions.\n" +
                    "2. If user asks non-medical → reply: 'I can only answer medical and health-related questions.'\n" +
                    "3. Give short but useful medical guidance (3–5 lines).\n" +
                    "4. You ARE allowed to recommend common OTC medicines like paracetamol, ORS, cough syrup etc.\n" +
                    "5. Never give prescription-only drugs.\n" +
                    "6. Always add safety line when needed: 'If symptoms worsen, consult a doctor.'\n" +
                    "7. If user asks identity → reply: 'I am DR-AI, a medical assistant. Founder: Vivek Yadav.'\n";


    public String ask(String prompt) {

        try {
            // Request body for Gemini API
            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "parts", List.of(
                                            Map.of("text", systemPrompt + "\nUser: " + prompt)
                                    )
                            )
                    )
            );

            // Call Gemini API
            Map response = restTemplate.postForObject(
                    url + "?key=" + apiKey,
                    requestBody,
                    Map.class
            );

            // Extract response text safely
            List candidates = (List) response.get("candidates");
            Map candidate = (Map) candidates.get(0);
            Map content = (Map) candidate.get("content");
            List parts = (List) content.get("parts");
            Map part0 = (Map) parts.get(0);

            return part0.get("text").toString();

        } catch (Exception e) {
            return "⚠️ DR-AI Error: Could not read AI response.";
        }
    }
}
