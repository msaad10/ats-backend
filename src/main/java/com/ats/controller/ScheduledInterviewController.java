package com.ats.controller;

import com.ats.model.ScheduledInterview;
import com.ats.service.ScheduledInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class ScheduledInterviewController {

    @Autowired
    private ScheduledInterviewService scheduledInterviewService;

    @PostMapping
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER')")
    public ResponseEntity<ScheduledInterview> scheduleInterview(@RequestBody ScheduledInterview interview) {
        return ResponseEntity.ok(scheduledInterviewService.scheduleInterview(interview));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER', 'INTERVIEWER', 'CANDIDATE')")
    public ResponseEntity<ScheduledInterview> getInterview(@PathVariable Long id) {
        return ResponseEntity.ok(scheduledInterviewService.getInterview(id));
    }

    @GetMapping("/job-candidate/{jobCandidateId}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER', 'INTERVIEWER', 'CANDIDATE')")
    public ResponseEntity<List<ScheduledInterview>> getInterviewsByJobCandidate(
            @PathVariable Long jobCandidateId) {
        return ResponseEntity.ok(scheduledInterviewService.getInterviewsByJobCandidate(jobCandidateId));
    }

    @GetMapping("/interviewer/{interviewerId}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER', 'INTERVIEWER')")
    public ResponseEntity<List<ScheduledInterview>> getInterviewsByInterviewer(
            @PathVariable Long interviewerId) {
        return ResponseEntity.ok(scheduledInterviewService.getInterviewsByInterviewer(interviewerId));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER', 'INTERVIEWER')")
    public ResponseEntity<List<ScheduledInterview>> getInterviewsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(scheduledInterviewService.getInterviewsByDateRange(start, end));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER', 'INTERVIEWER')")
    public ResponseEntity<ScheduledInterview> updateInterview(
            @PathVariable Long id,
            @RequestBody ScheduledInterview interview) {
        return ResponseEntity.ok(scheduledInterviewService.updateInterview(id, interview));
    }

    @PostMapping("/{id}/pass")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER', 'INTERVIEWER')")
    public ResponseEntity<ScheduledInterview> passInterview(
            @PathVariable Long id,
            @RequestParam(required = false) String feedback) {
        return ResponseEntity.ok(scheduledInterviewService.passInterview(id, feedback));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER', 'INTERVIEWER')")
    public ResponseEntity<ScheduledInterview> rejectInterview(
            @PathVariable Long id,
            @RequestParam(required = false) String feedback) {
        return ResponseEntity.ok(scheduledInterviewService.rejectInterview(id, feedback));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER', 'INTERVIEWER')")
    public ResponseEntity<Void> cancelInterview(@PathVariable Long id) {
        scheduledInterviewService.cancelInterview(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'HIRING_MANAGER')")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        scheduledInterviewService.deleteInterview(id);
        return ResponseEntity.ok().build();
    }
} 