package com.ats.dto.interview;

import com.ats.model.InterviewResult;
import lombok.Data;

@Data
public class UpdateInterviewStatusRequest {

    private InterviewResult result;

    private String feedback;
} 