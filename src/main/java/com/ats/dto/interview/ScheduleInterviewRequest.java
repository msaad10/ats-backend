package com.ats.dto.interview;

import com.ats.model.InterviewType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleInterviewRequest {
    @NotNull(message = "Job candidate ID is required")
    private Long jobCandidateId;

    @NotNull(message = "Interviewer ID is required")
    private Long interviewerId;

    @NotNull(message = "Interview date and time is required")
    private LocalDateTime dateTime;

    private String details;

    @NotNull(message = "Interview type is required")
    private InterviewType interviewType;
} 