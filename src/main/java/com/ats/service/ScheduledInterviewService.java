package com.ats.service;

import com.ats.model.InterviewResult;
import com.ats.model.ScheduledInterview;
import com.ats.repository.ScheduledInterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduledInterviewService {

    @Autowired
    private ScheduledInterviewRepository scheduledInterviewRepository;

    public ScheduledInterview scheduleInterview(ScheduledInterview interview) {
        // Validate that the interviewer is not already scheduled for this time
        List<ScheduledInterview> existingInterviews = scheduledInterviewRepository
                .findByInterviewerIdAndDateTimeBetween(
                        interview.getInterviewer().getId(),
                        interview.getDateTime().minusHours(1),
                        interview.getDateTime().plusHours(1)
                );
        
        if (!existingInterviews.isEmpty()) {
            throw new RuntimeException("Interviewer is already scheduled for this time slot");
        }

        // Set initial result as PENDING
        interview.setResult(InterviewResult.PENDING);
        return scheduledInterviewRepository.save(interview);
    }

    public ScheduledInterview getInterview(Long id) {
        return scheduledInterviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));
    }

    public List<ScheduledInterview> getInterviewsByJobCandidate(Long jobCandidateId) {
        return scheduledInterviewRepository.findByJobCandidateId(jobCandidateId);
    }

    public List<ScheduledInterview> getInterviewsByInterviewer(Long interviewerId) {
        return scheduledInterviewRepository.findByInterviewerId(interviewerId);
    }

    public List<ScheduledInterview> getInterviewsByDateRange(LocalDateTime start, LocalDateTime end) {
        return scheduledInterviewRepository.findByDateTimeBetween(start, end);
    }

    public ScheduledInterview updateInterview(Long id, ScheduledInterview interview) {
        ScheduledInterview existingInterview = getInterview(id);
        
        // Only allow updating certain fields
        existingInterview.setDateTime(interview.getDateTime());
        existingInterview.setDetails(interview.getDetails());
        existingInterview.setInterviewType(interview.getInterviewType());
        existingInterview.setResult(interview.getResult());
        existingInterview.setFeedback(interview.getFeedback());
        
        return scheduledInterviewRepository.save(existingInterview);
    }

    public ScheduledInterview passInterview(Long id, String feedback) {
        ScheduledInterview interview = getInterview(id);
        interview.setResult(InterviewResult.PASSED);
        interview.setFeedback(feedback);
        return scheduledInterviewRepository.save(interview);
    }

    public ScheduledInterview rejectInterview(Long id, String feedback) {
        ScheduledInterview interview = getInterview(id);
        interview.setResult(InterviewResult.FAILED);
        interview.setFeedback(feedback);
        return scheduledInterviewRepository.save(interview);
    }

    public void cancelInterview(Long id) {
        ScheduledInterview interview = getInterview(id);
        interview.setResult(InterviewResult.CANCELLED);
        scheduledInterviewRepository.save(interview);
    }

    public void deleteInterview(Long id) {
        scheduledInterviewRepository.deleteById(id);
    }
} 