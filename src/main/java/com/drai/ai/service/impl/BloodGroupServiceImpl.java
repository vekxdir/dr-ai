package com.drai.ai.service.impl;

import com.drai.ai.dto.BloodGroupRequest;
import com.drai.ai.dto.BloodGroupResponse;
import com.drai.ai.service.BloodGroupService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BloodGroupServiceImpl implements BloodGroupService {

    @Override
    public BloodGroupResponse checkCompatibility(BloodGroupRequest request) {
        String recipient = request.getBloodGroup();
        if (recipient == null) {
            return BloodGroupResponse.builder()
                    .recipientGroup("Unknown")
                    .compatibleDonors(Collections.emptyList())
                    .message("Please provide a valid blood group.")
                    .build();
        }

        List<String> donors = getCompatibleDonors(recipient.toUpperCase());

        return BloodGroupResponse.builder()
                .recipientGroup(recipient.toUpperCase())
                .compatibleDonors(donors)
                .message("Found " + donors.size() + " compatible donor types.")
                .build();
    }

    private List<String> getCompatibleDonors(String recipient) {
        switch (recipient) {
            case "A+":
                return Arrays.asList("A+", "A-", "O+", "O-");
            case "O+":
                return Arrays.asList("O+", "O-");
            case "B+":
                return Arrays.asList("B+", "B-", "O+", "O-");
            case "AB+":
                return Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
            case "A-":
                return Arrays.asList("A-", "O-");
            case "O-":
                return Collections.singletonList("O-");
            case "B-":
                return Arrays.asList("B-", "O-");
            case "AB-":
                return Arrays.asList("AB-", "A-", "B-", "O-");
            default:
                return Collections.emptyList();
        }
    }
}
