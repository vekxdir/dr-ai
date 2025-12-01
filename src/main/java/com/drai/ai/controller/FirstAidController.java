package com.drai.ai.controller;

import com.drai.ai.dto.FirstAidRequest;
import com.drai.ai.dto.FirstAidResponse;
import com.drai.ai.service.FirstAidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/first-aid")
@RequiredArgsConstructor
public class FirstAidController {

    private final FirstAidService service;

    @PostMapping("/ask")
    public ResponseEntity<FirstAidResponse> ask(@RequestBody FirstAidRequest request) {
        return ResponseEntity.ok(service.getFirstAid(request));
    }
}
