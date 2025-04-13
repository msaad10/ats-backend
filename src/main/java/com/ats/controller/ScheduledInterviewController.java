package com.ats.controller;

import com.ats.dto.interview.InterviewResponse;
import com.ats.dto.interview.ScheduleInterviewRequest;
import com.ats.dto.interview.UpdateInterviewRequest;
import com.ats.dto.interview.UpdateInterviewResultRequest;
import com.ats.mapper.InterviewMapper;
import com.ats.service.ScheduledInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interviews")
public class ScheduledInterviewController {

    @Autowired
    private ScheduledInterviewService scheduledInterviewService;

    @Autowired
    private InterviewMapper interviewMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER')")
    public ResponseEntity<InterviewResponse> scheduleInterview(@RequestBody ScheduleInterviewRequest request) {
        return ResponseEntity.ok(interviewMapper.toResponse(scheduledInterviewService.scheduleInterview(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER', 'CANDIDATE')")
    public ResponseEntity<InterviewResponse> getInterview(@PathVariable Long id) {
        return ResponseEntity.ok(interviewMapper.toResponse(scheduledInterviewService.getInterview(id)));
    }

    @GetMapping("/job-candidate/{candidateId}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER', 'CANDIDATE')")
    public ResponseEntity<List<InterviewResponse>> getInterviewsByJobCandidate(
            @PathVariable Long candidateId) {
        return ResponseEntity.ok(scheduledInterviewService.getInterviewsByJobCandidate(candidateId)
                .stream()
                .map(interviewMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/interviewer/{interviewerId}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER')")
    public ResponseEntity<List<InterviewResponse>> getInterviewsByInterviewer(
            @PathVariable Long interviewerId) {
        return ResponseEntity.ok(scheduledInterviewService.getInterviewsByInterviewer(interviewerId)
                .stream()
                .map(interviewMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER')")
    public ResponseEntity<List<InterviewResponse>> getInterviewsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(scheduledInterviewService.getInterviewsByDateRange(start, end)
                .stream()
                .map(interviewMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER')")
    public ResponseEntity<InterviewResponse> updateInterview(
            @PathVariable Long id,
            @RequestBody UpdateInterviewRequest request) {
        return ResponseEntity.ok(interviewMapper.toResponse(scheduledInterviewService.updateInterview(id, request)));
    }

    @PutMapping("/{id}/result")
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER')")
    public ResponseEntity<InterviewResponse> updateInterviewResult(
            @PathVariable Long id,
            @RequestBody UpdateInterviewResultRequest request) {
        return ResponseEntity.ok(interviewMapper.toResponse(scheduledInterviewService.updateInterviewResult(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER')")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        scheduledInterviewService.deleteInterview(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/candidate/{candidateId}")
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER', 'CANDIDATE')")
    public ResponseEntity<List<InterviewResponse>> getInterviewsByCandidate(
            @PathVariable Long candidateId) {
        return ResponseEntity.ok(scheduledInterviewService.getInterviewsByCandidateId(candidateId)
                .stream()
                .map(interviewMapper::toResponse)
                .collect(Collectors.toList()));
    }
} 