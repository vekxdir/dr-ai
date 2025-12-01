package com.drai.ai.service;

import com.drai.ai.dto.ReportResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ReportService {
    ReportResponse analyzeReport(MultipartFile file);
}
