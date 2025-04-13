package com.ats.mapper;

import com.ats.dto.jobcandidate.JobCandidateRequest;
import com.ats.dto.jobcandidate.JobCandidateResponse;
import com.ats.model.JobCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JobCandidateMapper {
    @Autowired
    private InterviewMapper interviewMapper;

    public JobCandidate toEntity(JobCandidateRequest request) {
        JobCandidate jobCandidate = new JobCandidate();
        jobCandidate.setNotes(request.getNotes());
        return jobCandidate;
    }

    public JobCandidateResponse toResponse(JobCandidate jobCandidate) {
        JobCandidateResponse response = new JobCandidateResponse();
        response.setId(jobCandidate.getId());
        response.setJobId(jobCandidate.getJob().getId());
        response.setJobDepartment(jobCandidate.getJob().getDepartment());
        response.setJobTitle(jobCandidate.getJob().getTitle());
        response.setUserId(jobCandidate.getUser().getId());
        response.setUserName(jobCandidate.getUser().getFullName());
        response.setEmail(jobCandidate.getUser().getEmail());
        response.setCurrentStage(jobCandidate.getCurrentStage());
        response.setNotes(jobCandidate.getNotes());
        response.setAppliedAt(jobCandidate.getAppliedAt().toString());
        response.setMatchScore(jobCandidate.getMatchScore());
        
        // Add scheduled interviews
        if (jobCandidate.getScheduledInterviews() != null) {
            response.setScheduledInterviews(
                jobCandidate.getScheduledInterviews().stream()
                    .map(interviewMapper::toResponse)
                    .collect(Collectors.toList())
            );
        }
        
        return response;
    }
} 