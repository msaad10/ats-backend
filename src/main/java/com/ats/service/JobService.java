package com.ats.service;

import com.ats.dto.job.JobRequest;
import com.ats.model.Job;
import com.ats.model.JobStatus;
import com.ats.repository.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public List<Job> getRecentJobs() {
        return jobRepository.findTop3ByOrderByCreatedAtDesc();
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found with id: " + id));
    }

    public Job createJob(JobRequest request) {
        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setDepartment(request.getDepartment());
        job.setStatus(JobStatus.OPEN);
        return jobRepository.save(job);
    }

    public Job updateJob(Long id, JobRequest request) {
        Job job = getJobById(id);
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setDepartment(request.getDepartment());
        return jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        Job job = getJobById(id);
        jobRepository.delete(job);
    }
} 