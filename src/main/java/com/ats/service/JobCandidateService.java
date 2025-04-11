package com.ats.service;

import com.ats.model.JobCandidate;
import com.ats.repository.JobCandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobCandidateService {

    @Autowired
    private JobCandidateRepository jobCandidateRepository;

    public JobCandidate createJobCandidate(JobCandidate jobCandidate) {
        if (jobCandidateRepository.existsByJobIdAndCandidateId(
                jobCandidate.getJob().getId(), 
                jobCandidate.getCandidate().getId())) {
            throw new RuntimeException("Candidate has already applied for this job");
        }
        return jobCandidateRepository.save(jobCandidate);
    }

    public JobCandidate getJobCandidate(Long id) {
        return jobCandidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobCandidate not found"));
    }

    public List<JobCandidate> getJobCandidatesByJob(Long jobId) {
        return jobCandidateRepository.findByJobId(jobId);
    }

    public List<JobCandidate> getJobCandidatesByCandidate(Long candidateId) {
        return jobCandidateRepository.findByCandidateId(candidateId);
    }

    public JobCandidate updateJobCandidate(Long id, JobCandidate jobCandidate) {
        JobCandidate existingJobCandidate = getJobCandidate(id);
        existingJobCandidate.setCurrentStage(jobCandidate.getCurrentStage());
        existingJobCandidate.setNotes(jobCandidate.getNotes());
        return jobCandidateRepository.save(existingJobCandidate);
    }

    public void deleteJobCandidate(Long id) {
        jobCandidateRepository.deleteById(id);
    }
} 