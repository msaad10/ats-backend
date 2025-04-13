package com.ats.dto.interview;

import com.ats.model.InterviewResult;
import lombok.Data;

import java.util.List;

@Data
public class UpdateInterviewResultRequest {

    private InterviewResult result;
    private String feedback;
    private List<InterviewScore> scores;
} 