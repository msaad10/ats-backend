package com.ats.controller;

import com.ats.model.User;
import com.ats.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;

    @GetMapping
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER')")
    public ResponseEntity<List<User>> getAllCandidates() {
        return ResponseEntity.ok(candidateService.getAllCandidates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getCandidateById(@PathVariable Long id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    @PostMapping("/resume")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(candidateService.uploadResume(file));
    }

    @GetMapping("/resume/{userId}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER')")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long userId) {
        Resource resource = candidateService.downloadResume(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
} 