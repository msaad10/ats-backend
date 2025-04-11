package com.ats.repository;

import com.ats.model.JobCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobCandidateRepository extends JpaRepository<JobCandidate, Long> {
    List<JobCandidate> findByJobId(Long jobId);
    List<JobCandidate> findByUserId(Long userId);
    boolean existsByJobIdAndUserId(Long jobId, Long userId);
} 