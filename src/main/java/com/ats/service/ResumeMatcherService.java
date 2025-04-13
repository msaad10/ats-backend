package com.ats.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        if (resumeText == null || jobDescription == null) return 0;

        // Convert to lowercase and extract words longer than 3 characters
        Set<String> resumeWords = Arrays.stream(resumeText.toLowerCase().split("\\W+"))
                .filter(word -> word.length() > 3)
                .collect(Collectors.toSet());

        Set<String> jobWords = Arrays.stream(jobDescription.toLowerCase().split("\\W+"))
                .filter(word -> word.length() > 3)
                .collect(Collectors.toSet());

        if (resumeWords.isEmpty() || jobWords.isEmpty()) {
            return 0;
        }

        // Find intersection
        Set<String> intersection = new HashSet<>(resumeWords);
        intersection.retainAll(jobWords);

        // Calculate score as overlap percentage
        double overlapRatio = (double) intersection.size() / jobWords.size();

        // Optional: apply a smoothing factor or boost for more nuanced score
        double score = overlapRatio * 100;

        return (int) Math.round(score);
    }
} 