package com.drai.ai.service.impl;

import com.drai.ai.client.GeminiClient;
import com.drai.ai.dto.FirstAidRequest;
import com.drai.ai.dto.FirstAidResponse;
import com.drai.ai.service.FirstAidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirstAidServiceImpl implements FirstAidService {

    private final GeminiClient geminiClient;

    @Override
    public FirstAidResponse getFirstAid(FirstAidRequest req) {
        String response = geminiClient.askFirstAid(req.getQuery());
        return FirstAidResponse.builder()
                .markdownContent(response)
                .build();
    }
}
