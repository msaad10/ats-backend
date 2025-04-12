package com.ats.dto.jobcandidate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobCandidateRequest {
    @NotNull(message = "Job ID is required")
    private Long jobId;

    @NotNull(message = "User ID is required")
    private Long userId;

    private String notes;
} 