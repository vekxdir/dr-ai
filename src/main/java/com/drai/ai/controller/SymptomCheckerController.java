package com.drai.ai.controller;

import com.drai.ai.dto.AnalysisResponse;
import com.drai.ai.dto.SymptomRequest;
import com.drai.ai.service.SymptomCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/symptoms")
@RequiredArgsConstructor
public class SymptomCheckerController {

    private final SymptomCheckerService service;

    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResponse> analyze(@RequestBody SymptomRequest req) {
        AnalysisResponse res = service.analyze(req);
        return ResponseEntity.ok(res);
    }
}
