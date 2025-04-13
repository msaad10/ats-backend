package com.ats.service;

import com.ats.dto.jobcandidate.JobCandidateRequest;
import com.ats.model.Job;
import com.ats.model.JobCandidate;
import com.ats.model.CandidateStage;
import com.ats.model.User;
import com.ats.repository.JobCandidateRepository;
import com.ats.repository.JobRepository;
import com.ats.repository.UserRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobCandidateService {

    @Autowired
    private JobCandidateRepository jobCandidateRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResumeMatcherService resumeMatcherService;
    @Autowired
    private EntityManager entityManager;

    public JobCandidate createJobCandidate(JobCandidateRequest request) {
        if (jobCandidateRepository.existsByJobIdAndUserId(
                request.getJobId(),
                request.getUserId())) {
            throw new RuntimeException("User has already applied for this job");
        }

        Job job = jobRepository.findById(request.getJobId()).orElse(null);
        User user = userRepository.findById(request.getUserId()).orElse(null);

        if (job == null) {
            throw new RuntimeException("Job not found");
        }

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!Boolean.TRUE.equals(user.getIsResumeUploaded())) {
            throw new RuntimeException("Resume not uploaded");
        }

        File resumeFile = new File("src/main/resources/resumes/" + user.getId() + ".pdf");
        int match = resumeMatcherService.match(resumeFile, job.getDescription());
        JobCandidate jobCandidate = new JobCandidate();
        jobCandidate.setJob(job);
        jobCandidate.setUser(user);
        jobCandidate.setNotes(request.getNotes());
        jobCandidate.setMatchScore(match);
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
        EntityGraph<JobCandidate> entityGraph = entityManager.createEntityGraph(JobCandidate.class);
        entityGraph.addAttributeNodes("scheduledInterviews");
        entityGraph.addAttributeNodes("job");
        entityGraph.addAttributeNodes("user");

        return entityManager.createQuery(
                "SELECT jc FROM JobCandidate jc WHERE jc.user.id = :userId", JobCandidate.class)
                .setParameter("userId", userId)
                .setHint("jakarta.persistence.loadgraph", entityGraph)
                .getResultList();
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