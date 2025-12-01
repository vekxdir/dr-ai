package com.drai.ai.service.impl;

import com.drai.ai.dto.ChatResponseDto;
import com.drai.ai.router.AiRouterService;
import com.drai.ai.service.AiChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiChatServiceImpl implements AiChatService {

    private final AiRouterService router;

    @Override
    public ChatResponseDto chat(String userId, String msg) {

        String reply = router.route(msg);

        return ChatResponseDto.builder()
                .answer(reply)
                .model("gemini-pro")
                .build();
    }
}
