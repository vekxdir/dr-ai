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

        // 🔥 System Prompt for Chat (Short, conversational)
        private final String CHAT_SYSTEM_PROMPT = "You are DR-AI, a medical assistant designed for safe and helpful replies. "
                        +
                        "Founder: Vivek Yadav.\n\n" +
                        "RULES:\n" +
                        "1. You ONLY answer health and medical questions.\n" +
                        "2. If user asks non-medical → reply: 'I can only answer medical and health-related questions.'\n"
                        +
                        "3. Give short but useful medical guidance (3–5 lines).\n" +
                        "4. You ARE allowed to recommend common OTC medicines like paracetamol, ORS, cough syrup etc.\n"
                        +
                        "5. Never give prescription-only drugs.\n" +
                        "6. Always add safety line when needed: 'If symptoms worsen, consult a doctor.'\n";

        // 🔥 System Prompt for Report Analysis (Detailed, analytical)
        private final String REPORT_SYSTEM_PROMPT = "You are an expert Medical Report Analyser. " +
                        "Your task is to analyze medical reports (text or images) and provide a detailed, easy-to-understand summary.\n"
                        +
                        "RULES:\n" +
                        "1. Extract key medical values and findings.\n" +
                        "2. Explain any abnormal values in simple terms.\n" +
                        "3. Provide general health advice based on the findings.\n" +
                        "4. Format the output in clean Markdown.\n" +
                        "5. If the input is NOT a medical report, reply: 'This does not appear to be a medical report.'\n";

        // 🔥 System Prompt for First Aid (Urgent, Clear, Step-by-Step)
        private final String FIRST_AID_SYSTEM_PROMPT = "You are an expert First Aid Assistant. " +
                        "Provide immediate, step-by-step instructions for the emergency described.\n" +
                        "RULES:\n" +
                        "1. Keep it concise and action-oriented.\n" +
                        "2. Use BOLD text for key actions.\n" +
                        "3. Always start with '⚠️ Call Emergency Services immediately if the condition is critical.'\n"
                        +
                        "4. Format as a clear Markdown list.\n" +
                        "5. Do not provide long explanations, just instructions.\n";

        // 🔥 System Prompt for Symptom Checker (JSON Output)
        private final String SYMPTOM_CHECKER_SYSTEM_PROMPT = "You are an expert Medical Symptom Checker. " +
                        "Analyze the user's symptoms and provide a list of potential conditions.\n" +
                        "RULES:\n" +
                        "1. Return ONLY valid JSON. No markdown formatting.\n" +
                        "2. The output must be a JSON Array of objects.\n" +
                        "3. Each object must have keys: 'name', 'overview', 'treatment', 'matchScore'.\n" +
                        "4. 'matchScore' must be a double between 0.0 and 1.0 indicating likelihood.\n" +
                        "5. Provide 3-5 potential conditions.\n";

        public String ask(String prompt) {
                return callApi(createRequestBody(CHAT_SYSTEM_PROMPT, prompt));
        }

        public String askForAnalysis(String prompt) {
                return callApi(createRequestBody(REPORT_SYSTEM_PROMPT, prompt));
        }

        public String askFirstAid(String prompt) {
                return callApi(createRequestBody(FIRST_AID_SYSTEM_PROMPT, prompt));
        }

        public String askSymptomCheck(String prompt) {
                return callApi(createRequestBody(SYMPTOM_CHECKER_SYSTEM_PROMPT, prompt));
        }

        private Map<String, Object> createRequestBody(String sysPrompt, String userPrompt) {
                return Map.of(
                                "contents", List.of(
                                                Map.of("parts", List.of(
                                                                Map.of("text", sysPrompt + "\nUser: " + userPrompt)))));
        }

        public String analyzeImage(String prompt, String base64Image, String mimeType) {
                try {
                        Map<String, Object> requestBody = Map.of(
                                        "contents", List.of(
                                                        Map.of("parts", List.of(
                                                                        Map.of("text", REPORT_SYSTEM_PROMPT + "\nUser: "
                                                                                        + prompt),
                                                                        Map.of("inline_data", Map.of(
                                                                                        "mime_type", mimeType,
                                                                                        "data", base64Image))))));
                        return callApi(requestBody);
                } catch (Exception e) {
                        return "⚠️ DR-AI Error: " + e.getMessage();
                }
        }

        private String callApi(Map<String, Object> requestBody) {
                Map response = restTemplate.postForObject(url + "?key=" + apiKey, requestBody, Map.class);
                List candidates = (List) response.get("candidates");
                Map candidate = (Map) candidates.get(0);
                Map content = (Map) candidate.get("content");
                List parts = (List) content.get("parts");
                Map part0 = (Map) parts.get(0);
                return part0.get("text").toString();
        }
}
