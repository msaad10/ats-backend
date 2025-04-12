package com.ats.mapper;

import com.ats.dto.jobcandidate.JobCandidateRequest;
import com.ats.dto.jobcandidate.JobCandidateResponse;
import com.ats.model.JobCandidate;
import org.springframework.stereotype.Component;

@Component
public class JobCandidateMapper {
    public JobCandidate toEntity(JobCandidateRequest request) {
        JobCandidate jobCandidate = new JobCandidate();
        jobCandidate.setNotes(request.getNotes());
        return jobCandidate;
    }

    public JobCandidateResponse toResponse(JobCandidate jobCandidate) {
        JobCandidateResponse response = new JobCandidateResponse();
        response.setId(jobCandidate.getId());
        response.setJobId(jobCandidate.getJob().getId());
        response.setJobTitle(jobCandidate.getJob().getTitle());
        response.setUserId(jobCandidate.getUser().getId());
        response.setUserName(jobCandidate.getUser().getUsername());
        response.setCurrentStage(jobCandidate.getCurrentStage());
        response.setNotes(jobCandidate.getNotes());
        response.setAppliedAt(jobCandidate.getAppliedAt().toString());
        return response;
    }
} 