package com.ats.service;

import com.ats.dto.interview.ScheduleInterviewRequest;
import com.ats.dto.interview.UpdateInterviewRequest;
import com.ats.model.CandidateStage;
import com.ats.model.InterviewResult;
import com.ats.model.JobCandidate;
import com.ats.model.ScheduledInterview;
import com.ats.model.User;
import com.ats.repository.JobCandidateRepository;
import com.ats.repository.ScheduledInterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledInterviewService {

    @Autowired
    private ScheduledInterviewRepository scheduledInterviewRepository;
    @Autowired
    private JobCandidateService jobCandidateService;
    @Autowired
    private UserService userService;
    @Autowired
    private JobCandidateRepository jobCandidateRepository;

    public ScheduledInterview scheduleInterview(ScheduleInterviewRequest request) {
        // Validate that the interviewer is not already scheduled for this time
        List<ScheduledInterview> existingInterviews = scheduledInterviewRepository
                .findByInterviewerIdAndDateTimeBetween(
                        request.getInterviewerId(),
                        request.getDateTime().minusHours(1),
                        request.getDateTime().plusHours(1)
                );
        
        if (!existingInterviews.isEmpty()) {
            throw new RuntimeException("Interviewer is already scheduled for this time slot");
        }

        JobCandidate jobCandidate = jobCandidateService.getJobCandidate(request.getJobCandidateId());
        Optional<User> user = userService.getUserById(request.getInterviewerId());
        jobCandidate.setCurrentStage(CandidateStage.INTERVIEWING.toString());
        jobCandidateRepository.save(jobCandidate);

        ScheduledInterview interview = new ScheduledInterview();
        interview.setJobCandidate(jobCandidate);
        interview.setInterviewer(user.get());
        interview.setDateTime(request.getDateTime());
        interview.setDetails(request.getDetails());
        interview.setInterviewType(request.getInterviewType());
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

    public ScheduledInterview updateInterview(Long id, UpdateInterviewRequest request) {
        ScheduledInterview existingInterview = getInterview(id);
        existingInterview.setDateTime(request.getDateTime());
        existingInterview.setDetails(request.getDetails());
        existingInterview.setInterviewType(request.getInterviewType());
        existingInterview.setResult(request.getResult());
        existingInterview.setFeedback(request.getFeedback());
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

    public List<ScheduledInterview> getInterviewsByCandidateId(Long candidateId) {
        return scheduledInterviewRepository.findByJobCandidate_User_Id(candidateId);
    }
} 