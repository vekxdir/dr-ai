package com.drai.ai.controller;

import com.drai.ai.dto.BloodGroupRequest;
import com.drai.ai.dto.BloodGroupResponse;
import com.drai.ai.service.BloodGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blood")
@RequiredArgsConstructor
public class BloodGroupController {

    private final BloodGroupService service;

    @PostMapping("/check")
    public ResponseEntity<BloodGroupResponse> check(@RequestBody BloodGroupRequest request) {
        return ResponseEntity.ok(service.checkCompatibility(request));
    }
}
