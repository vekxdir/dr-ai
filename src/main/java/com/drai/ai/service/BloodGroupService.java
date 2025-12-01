package com.drai.ai.service;

import com.drai.ai.dto.BloodGroupRequest;
import com.drai.ai.dto.BloodGroupResponse;

public interface BloodGroupService {
    BloodGroupResponse checkCompatibility(BloodGroupRequest request);
}
