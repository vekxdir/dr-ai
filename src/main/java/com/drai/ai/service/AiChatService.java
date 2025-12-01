package com.drai.ai.service;

import com.drai.ai.dto.ChatResponseDto;

public interface AiChatService {
    ChatResponseDto chat(String userId, String msg);
}
