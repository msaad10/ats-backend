package com.ats.mapper;

import com.ats.dto.job.JobRequest;
import com.ats.dto.job.JobResponse;
import com.ats.model.Job;
import com.ats.repository.JobCandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    @Autowired
    private JobCandidateRepository jobCandidateRepository;

    public Job toEntity(JobRequest request) {
        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setDepartment(request.getDepartment());
        return job;
    }

    public JobResponse toResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setId(job.getId());
        response.setTitle(job.getTitle());
        response.setDescription(job.getDescription());
        response.setLocation(job.getLocation());
        response.setDepartment(job.getDepartment());
        response.setStatus(String.valueOf(job.getStatus()));
        response.setCreatedAt(job.getCreatedAt().toString());
        response.setUpdatedAt(job.getUpdatedAt().toString());
        response.setApplicationCount(jobCandidateRepository.countByJobId(job.getId()));
        return response;
    }
} 