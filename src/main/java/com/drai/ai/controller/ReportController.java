package com.drai.ai.controller;

import com.drai.ai.dto.ReportRequest;
import com.drai.ai.dto.ReportResponse;
import com.drai.ai.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    @PostMapping("/analyze")
    public ResponseEntity<ReportResponse> analyze(
            @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        return ResponseEntity.ok(service.analyzeReport(file));
    }
}
