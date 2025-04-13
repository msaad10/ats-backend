package com.ats.dto.job;

import lombok.Data;

@Data
public class JobResponse {
    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String department;
    private String location;
    private String employmentType;
    private String experienceLevel;
    private String salaryRange;
    private String status;
    private String createdAt;
    private String updatedAt;
    private long applicationCount;
} 