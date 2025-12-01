package com.drai.ai.controller;

import com.drai.ai.dto.ChatRequestDto;
import com.drai.ai.dto.ChatResponseDto;
import com.drai.ai.service.AiChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AiChatController {

    private final AiChatService service;

    @PostMapping("/api/chat")
    public ResponseEntity<ChatResponseDto> chat(@RequestBody ChatRequestDto req) {
        return ResponseEntity.ok(service.chat(req.getUserId(), req.getMessage()));
    }
}
