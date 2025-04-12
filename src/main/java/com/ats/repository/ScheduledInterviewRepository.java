package com.ats.repository;

import com.ats.model.ScheduledInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduledInterviewRepository extends JpaRepository<ScheduledInterview, Long> {
    List<ScheduledInterview> findByJobCandidateId(Long jobCandidateId);
    List<ScheduledInterview> findByInterviewerId(Long interviewerId);
    List<ScheduledInterview> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<ScheduledInterview> findByJobCandidateIdAndResult(Long jobCandidateId, String result);
    List<ScheduledInterview> findByInterviewerIdAndDateTimeBetween(
            Long interviewerId, LocalDateTime start, LocalDateTime end);
    List<ScheduledInterview> findByJobCandidate_User_Id(Long userId);
} 