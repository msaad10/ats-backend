package com.ats.controller;

import com.ats.model.JobCandidate;
import com.ats.service.JobCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-candidates")
public class JobCandidateController {

    @Autowired
    private JobCandidateService jobCandidateService;

    @PostMapping
    public ResponseEntity<JobCandidate> createJobCandidate(@RequestBody JobCandidate jobCandidate) {
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JobCandidate>> getJobCandidatesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(jobCandidateService.getJobCandidatesByUser(userId));
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