package com.ats.mapper;

import com.ats.dto.interview.InterviewResponse;
import com.ats.dto.interview.ScheduleInterviewRequest;
import com.ats.dto.interview.UpdateInterviewRequest;
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
    }

    public InterviewResponse toResponse(ScheduledInterview interview) {
        InterviewResponse response = new InterviewResponse();
        response.setId(interview.getId());
        response.setJobCandidateId(interview.getJobCandidate().getId());
        response.setInterviewerId(interview.getInterviewer().getId());
        response.setInterviewerName(interview.getInterviewer().getFirstName() + " " + interview.getInterviewer().getLastName());
        response.setCandidateName(interview.getJobCandidate().getUser().getFirstName() + " " + interview.getJobCandidate().getUser().getLastName());
        response.setJobTitle(interview.getJobCandidate().getJob().getTitle());
        response.setDateTime(interview.getDateTime());
        response.setDetails(interview.getDetails());
        response.setInterviewType(interview.getInterviewType());
        response.setResult(interview.getResult());
        response.setFeedback(interview.getFeedback());
        return response;
    }
} 