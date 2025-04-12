package com.ats.dto.interview;

import com.ats.model.InterviewResult;
import com.ats.model.InterviewType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InterviewResponse {
    private Long id;
    private Long jobCandidateId;
    private Long candidateId;
    private Long interviewerId;
    private String interviewerName;
    private String candidateName;
    private String jobTitle;
    private LocalDateTime dateTime;
    private String details;
    private InterviewType interviewType;
    private InterviewResult result;
    private String feedback;
} 