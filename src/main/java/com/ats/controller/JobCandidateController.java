package com.ats.controller;

import com.ats.dto.jobcandidate.JobCandidateRequest;
import com.ats.dto.jobcandidate.JobCandidateResponse;
import com.ats.mapper.JobCandidateMapper;
import com.ats.service.JobCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/job-candidates")
public class JobCandidateController {

    @Autowired
    private JobCandidateService jobCandidateService;

    @Autowired
    private JobCandidateMapper jobCandidateMapper;

    @PostMapping("/apply")
    @PreAuthorize("hasAnyRole('RECRUITER', 'CANDIDATE')")
    public ResponseEntity<JobCandidateResponse> applyJob(@RequestBody JobCandidateRequest request) {
        return ResponseEntity.ok(jobCandidateMapper.toResponse(
                jobCandidateService.createJobCandidate(jobCandidateMapper.toEntity(request))));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER', 'INTERVIEWER', 'CANDIDATE')")
    public ResponseEntity<JobCandidateResponse> getJobCandidate(@PathVariable Long id) {
        return ResponseEntity.ok(jobCandidateMapper.toResponse(jobCandidateService.getJobCandidate(id)));
    }

    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER', 'INTERVIEWER')")
    public ResponseEntity<List<JobCandidateResponse>> getJobCandidatesByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobCandidateService.getJobCandidatesByJob(jobId)
                .stream()
                .map(jobCandidateMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER', 'INTERVIEWER', 'CANDIDATE')")
    public ResponseEntity<List<JobCandidateResponse>> getJobCandidatesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(jobCandidateService.getJobCandidatesByUser(userId)
                .stream()
                .map(jobCandidateMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER')")
    public ResponseEntity<JobCandidateResponse> updateJobCandidate(
            @PathVariable Long id,
            @RequestBody JobCandidateRequest request) {
        return ResponseEntity.ok(jobCandidateMapper.toResponse(
                jobCandidateService.updateJobCandidate(id, jobCandidateMapper.toEntity(request))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER')")
    public ResponseEntity<Void> deleteJobCandidate(@PathVariable Long id) {
        jobCandidateService.deleteJobCandidate(id);
        return ResponseEntity.ok().build();
    }
} 