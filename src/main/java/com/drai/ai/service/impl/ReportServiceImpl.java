package com.drai.ai.service.impl;

import com.drai.ai.client.GeminiClient;
import com.drai.ai.dto.ReportRequest;
import com.drai.ai.dto.ReportResponse;
import com.drai.ai.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final GeminiClient geminiClient;

    @Override
    public ReportResponse analyzeReport(MultipartFile file) {
        try {
            String contentType = file.getContentType();
            String response;

            if (contentType != null && contentType.equals("application/pdf")) {
                // Handle PDF
                String text = extractTextFromPdf(file);
                response = geminiClient.askForAnalysis("Analyze this medical report text: \n" + text);
            } else if (contentType != null && contentType.startsWith("image/")) {
                // Handle Image
                String base64 = Base64.getEncoder().encodeToString(file.getBytes());
                response = geminiClient.analyzeImage(
                        "This is an image of a medical laboratory report. Please extract the data and provide a detailed analysis.",
                        base64, contentType);
            } else {
                return ReportResponse.builder().analysis("⚠️ Unsupported file type. Please upload PDF or Image.")
                        .build();
            }

            return ReportResponse.builder().analysis(response).build();

        } catch (Exception e) {
            return ReportResponse.builder().analysis("⚠️ Error analyzing report: " + e.getMessage()).build();
        }
    }

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}
