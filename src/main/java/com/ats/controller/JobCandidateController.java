package com.ats.controller;

import com.ats.model.JobCandidate;
import com.ats.service.JobCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-candidates")
public class JobCandidateController {

    @Autowired
    private JobCandidateService jobCandidateService;

    @PostMapping("/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<JobCandidate> applyJob(@RequestBody JobCandidate jobCandidate) {
        return ResponseEntity.ok(jobCandidateService.createJobCandidate(jobCandidate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCandidate> getJobCandidate(@PathVariable Long id) {
        return ResponseEntity.ok(jobCandidateService.getJobCandidate(id));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobCandidate>> getJobCandidatesByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobCandidateService.getJobCandidatesByJob(jobId));
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<JobCandidate>> getJobCandidatesByCandidate(@PathVariable Long candidateId) {
        return ResponseEntity.ok(jobCandidateService.getJobCandidatesByCandidate(candidateId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobCandidate> updateJobCandidate(
            @PathVariable Long id,
            @RequestBody JobCandidate jobCandidate) {
        return ResponseEntity.ok(jobCandidateService.updateJobCandidate(id, jobCandidate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobCandidate(@PathVariable Long id) {
        jobCandidateService.deleteJobCandidate(id);
        return ResponseEntity.ok().build();
    }
} 