package com.ats.dto.interview;

import com.ats.model.InterviewResult;
import com.ats.model.InterviewType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateInterviewRequest {
    @NotNull(message = "Interview date and time is required")
    private LocalDateTime dateTime;

    private String details;

    @NotNull(message = "Interview type is required")
    private InterviewType interviewType;

    private InterviewResult result;

    private String feedback;

    public List<InterviewScore> interviewScores;
} 