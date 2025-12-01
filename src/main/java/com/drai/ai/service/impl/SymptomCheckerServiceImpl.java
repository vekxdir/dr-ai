package com.drai.ai.service.impl;

import com.drai.ai.dto.AnalysisResponse;
import com.drai.ai.dto.ConditionItem;
import com.drai.ai.dto.SymptomRequest;
import com.drai.ai.service.SymptomCheckerService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple rule-based matcher for demo. Extend with real medical knowledge later.
 */
@Service
public class SymptomCheckerServiceImpl implements SymptomCheckerService {

    // simple map of condition -> set of associated symptoms
    private static final Map<String, Set<String>> CONDITION_SYMPTOMS = new HashMap<>();

    static {
        CONDITION_SYMPTOMS.put("Influenza (Flu)", set("fever","cough","body aches","headache","chills","sore throat"));
        CONDITION_SYMPTOMS.put("Common Cold", set("runny nose","sore throat","sneezing","cough","headache"));
        CONDITION_SYMPTOMS.put("Bronchitis", set("cough","chest pain","shortness of breath","fever"));
        CONDITION_SYMPTOMS.put("COVID-19", set("fever","dry cough","loss of smell","loss of taste","shortness of breath"));
        CONDITION_SYMPTOMS.put("Strep Throat", set("sore throat","fever","headache"));
        CONDITION_SYMPTOMS.put("Migraine", set("headache","nausea","sensitivity to light"));
        CONDITION_SYMPTOMS.put("Gastroenteritis", set("nausea","vomiting","diarrhea","fever"));
    }

    private static Set<String> set(String... s) {
        return Arrays.stream(s).collect(Collectors.toSet());
    }

    @Override
    public AnalysisResponse analyze(SymptomRequest req) {
        List<String> inputSymptoms = Optional.ofNullable(req.getSymptoms()).orElse(Collections.emptyList())
                .stream().map(String::toLowerCase).collect(Collectors.toList());

        // score conditions by matched symptom count / total condition symptoms
        List<ConditionItem> results = new ArrayList<>();
        for (var entry : CONDITION_SYMPTOMS.entrySet()) {
            String condition = entry.getKey();
            Set<String> condSymptoms = entry.getValue();

            long matched = inputSymptoms.stream().filter(condSymptoms::contains).count();
            if (matched == 0) continue;

            double score = (double) matched / (double) condSymptoms.size();
            // Build overview & treatment text (simple static text; expand later)
            String overview = condition + " — common symptoms: " + String.join(", ", condSymptoms) + ".";
            String treatment = treatmentFor(condition);

            results.add(ConditionItem.builder()
                    .name(condition)
                    .overview(overview)
                    .treatment(treatment)
                    .matchScore(Math.round(score * 100.0) / 100.0)
                    .build());
        }

        // sort by score desc
        results.sort(Comparator.comparing(ConditionItem::getMatchScore).reversed());

        return AnalysisResponse.builder().conditions(results).build();
    }

    private String treatmentFor(String condition) {
        switch (condition) {
            case "Influenza (Flu)":
                return "Rest, fluids, paracetamol/ibuprofen for fever/pain. See doctor if high fever or difficulty breathing.";
            case "Common Cold":
                return "Rest, fluids, saline nose drops, lozenges. Over-the-counter decongestants if needed.";
            case "Bronchitis":
                return "Rest, humidifier, avoid smoking. See a doctor if cough is severe or lasts more than 3 weeks.";
            case "COVID-19":
                return "Isolate, rest, symptomatic care. Get PCR/antigen test and seek medical care if breathing difficulty.";
            case "Strep Throat":
                return "See doctor—antibiotics if confirmed. Rest and salt-water gargles for relief.";
            case "Migraine":
                return "Rest in a dark, quiet room; use migraine meds as prescribed. See specialist for frequent attacks.";
            case "Gastroenteritis":
                return "Hydration (oral rehydration solution), rest. See doctor if severe dehydration or bloody stool.";
            default:
                return "Supportive care and see a healthcare professional for diagnosis and definitive treatment.";
        }
    }
}
