package com.ats.dto.jobcandidate;

import lombok.Data;

@Data
public class JobCandidateResponse {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private Long userId;
    private String userName;
    private String email;
    private String currentStage;
    private String notes;
    private String appliedAt;
} 