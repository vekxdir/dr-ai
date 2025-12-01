package com.drai.ai.service;

import com.drai.ai.dto.DietRequest;
import com.drai.ai.dto.DietResponse;

public interface DietService {
    DietResponse generateDiet(DietRequest request);
}
