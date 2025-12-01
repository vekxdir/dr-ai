package com.drai.ai.service;

import com.drai.ai.dto.AnalysisResponse;
import com.drai.ai.dto.SymptomRequest;

public interface SymptomCheckerService {
    AnalysisResponse analyze(SymptomRequest req);
}
