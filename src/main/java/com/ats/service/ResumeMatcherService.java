package com.ats.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResumeMatcherService {

    public int match(File resumePdf, String jobDescription) {
        try {
            // Extract text from PDF
            String resumeText = extractTextFromPdf(resumePdf);
            
            // Calculate match score
            return calculateMatchScore(resumeText, jobDescription);
        } catch (IOException e) {
            log.error("Error processing resume PDF", e);
            throw new RuntimeException("Failed to process resume PDF", e);
        }
    }

    private String extractTextFromPdf(File pdfFile) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private int calculateMatchScore(String resumeText, String jobDescription) {
        // Convert both texts to lowercase and split into words
        List<String> resumeWords = Arrays.stream(resumeText.toLowerCase().split("\\W+"))
                .filter(word -> word.length() > 3) // Filter out short words
                .collect(Collectors.toList());
        
        List<String> jobWords = Arrays.stream(jobDescription.toLowerCase().split("\\W+"))
                .filter(word -> word.length() > 3)
                .collect(Collectors.toList());

        // Count matching words
        long matchingWords = resumeWords.stream()
                .filter(jobWords::contains)
                .count();

        // Calculate score (0-100)
        if (jobWords.isEmpty()) {
            return 0;
        }

        double score = ((double) matchingWords / jobWords.size()) * 100;
        return (int) Math.min(100, Math.max(0, score));
    }
} 