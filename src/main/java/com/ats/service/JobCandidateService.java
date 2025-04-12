package com.ats.service;

import com.ats.dto.jobcandidate.JobCandidateRequest;
import com.ats.model.JobCandidate;
import com.ats.model.CandidateStage;
import com.ats.repository.JobCandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobCandidateService {

    @Autowired
    private JobCandidateRepository jobCandidateRepository;

    public JobCandidate createJobCandidate(JobCandidateRequest request) {
        if (jobCandidateRepository.existsByJobIdAndUserId(
                request.getJobId(), 
                request.getUserId())) {
            throw new RuntimeException("User has already applied for this job");
        }
        
        JobCandidate jobCandidate = new JobCandidate();
        jobCandidate.setNotes(request.getNotes());
        jobCandidate.setCurrentStage(CandidateStage.APPLIED.toString());
        jobCandidate.setAppliedAt(LocalDateTime.now());
        return jobCandidateRepository.save(jobCandidate);
    }

    public JobCandidate getJobCandidate(Long id) {
        return jobCandidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobCandidate not found"));
    }

    public List<JobCandidate> getJobCandidatesByJob(Long jobId) {
        return jobCandidateRepository.findByJobId(jobId);
    }

    public List<JobCandidate> getJobCandidatesByUser(Long userId) {
        return jobCandidateRepository.findByUserId(userId);
    }

    public JobCandidate updateJobCandidate(Long id, JobCandidateRequest request) {
        JobCandidate existingJobCandidate = getJobCandidate(id);
        existingJobCandidate.setNotes(request.getNotes());
        return jobCandidateRepository.save(existingJobCandidate);
    }

    public void deleteJobCandidate(Long id) {
        jobCandidateRepository.deleteById(id);
    }
} 