package com.drai.ai.controller;

import com.drai.ai.dto.DietRequest;
import com.drai.ai.dto.DietResponse;
import com.drai.ai.service.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diet")
@RequiredArgsConstructor
public class DietController {

    private final DietService service;

    @PostMapping("/generate")
    public ResponseEntity<DietResponse> generate(@RequestBody DietRequest request) {
        return ResponseEntity.ok(service.generateDiet(request));
    }
}
