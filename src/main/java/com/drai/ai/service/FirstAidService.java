package com.drai.ai.service;

import com.drai.ai.dto.FirstAidRequest;
import com.drai.ai.dto.FirstAidResponse;

public interface FirstAidService {
    FirstAidResponse getFirstAid(FirstAidRequest request);
}
