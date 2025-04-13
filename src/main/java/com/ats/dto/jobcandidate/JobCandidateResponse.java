package com.ats.dto.jobcandidate;

import com.ats.dto.interview.InterviewResponse;
import lombok.Data;

import java.util.List;

@Data
public class JobCandidateResponse {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private String jobDepartment;
    private Long userId;
    private String userName;
    private String email;
    private String currentStage;
    private String notes;
    private String appliedAt;
    private int matchScore;
    private List<InterviewResponse> scheduledInterviews;
} 