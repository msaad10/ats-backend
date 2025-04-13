package com.ats.mapper;

import com.ats.dto.interview.InterviewResponse;
import com.ats.dto.interview.ScheduleInterviewRequest;
import com.ats.dto.interview.UpdateInterviewRequest;
import com.ats.model.InterviewType;
import com.ats.model.ScheduledInterview;
import org.springframework.stereotype.Component;

@Component
public class InterviewMapper {
    public ScheduledInterview toEntity(ScheduleInterviewRequest request) {
        ScheduledInterview interview = new ScheduledInterview();
        interview.setDateTime(request.getDateTime());
        interview.setDetails(request.getDetails());
        interview.setInterviewType(request.getInterviewType());
        return interview;
    }

    public void updateEntity(ScheduledInterview interview, UpdateInterviewRequest request) {
        interview.setDateTime(request.getDateTime());
        interview.setDetails(request.getDetails());
        interview.setInterviewType(request.getInterviewType());
        interview.setResult(request.getResult());
        interview.setFeedback(request.getFeedback());
        interview.setInterviewScores(request.getInterviewScores());
    }

    public InterviewResponse toResponse(ScheduledInterview interview) {
        InterviewResponse response = new InterviewResponse();
        response.setId(interview.getId());
        response.setJobCandidateId(interview.getJobCandidate().getId());
        response.setCandidateId(interview.getJobCandidate().getUser().getId());
        response.setInterviewerId(interview.getInterviewer().getId());
        response.setInterviewerName(interview.getInterviewer().getFirstName() + " " + interview.getInterviewer().getLastName());
        response.setCandidateName(interview.getJobCandidate().getUser().getFirstName() + " " + interview.getJobCandidate().getUser().getLastName());
        response.setJobTitle(interview.getJobCandidate().getJob().getTitle());
        response.setDateTime(interview.getDateTime());
        response.setDetails(interview.getDetails());
        response.setInterviewType(formatInterviewType(interview.getInterviewType()));
        response.setResult(interview.getResult());
        response.setFeedback(interview.getFeedback());
        response.setInterviewScores(interview.getInterviewScores());
        return response;
    }

    private String formatInterviewType(InterviewType interviewType) {
        if (interviewType == null) {
            return null;
        }
        // Split by underscore and capitalize each word
        String[] words = interviewType.name().split("_");
        StringBuilder formatted = new StringBuilder();
        for (String word : words) {
            if (formatted.length() > 0) {
                formatted.append(" ");
            }
            formatted.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1).toLowerCase());
        }
        return formatted.toString();
    }
} 