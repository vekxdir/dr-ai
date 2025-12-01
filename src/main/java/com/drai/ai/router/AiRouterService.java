package com.drai.ai.router;

import com.drai.ai.client.GeminiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiRouterService {

    private final GeminiClient gemini;

    public String route(String msg) {
        return gemini.ask(msg);   // ONLY GEMINI NOW
    }
}
