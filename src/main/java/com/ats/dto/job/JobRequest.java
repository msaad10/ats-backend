package com.ats.dto.job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Requirements are required")
    private String requirements;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Hiring manager ID is required")
    private Long hiringManagerId;

    private String location;
    private String employmentType;
    private String experienceLevel;
    private String salaryRange;
} 