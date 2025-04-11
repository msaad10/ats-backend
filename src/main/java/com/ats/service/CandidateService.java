package com.ats.service;

import com.ats.model.Candidate;
import com.ats.model.CandidateStage;
import com.ats.model.Job;
import com.ats.repository.CandidateRepository;
import com.ats.repository.JobRepository;
import com.ats.model.User;
import com.ats.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private static final String UPLOAD_DIR = "src/main/resources/resumes/";

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + id));
    }

    public Candidate createCandidate(Candidate candidate, Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Job not found with id: " + jobId));
        candidate.setAppliedJob(job);
        candidate.setCurrentStage(CandidateStage.APPLIED);
        return candidateRepository.save(candidate);
    }

    public Candidate updateCandidate(Long id, Candidate candidateDetails) {
        Candidate candidate = getCandidateById(id);
        candidate.setName(candidateDetails.getName());
        candidate.setEmail(candidateDetails.getEmail());
        candidate.setPhone(candidateDetails.getPhone());
        candidate.setCurrentStage(candidateDetails.getCurrentStage());
        return candidateRepository.save(candidate);
    }

    public void deleteCandidate(Long id) {
        Candidate candidate = getCandidateById(id);
        candidateRepository.delete(candidate);
    }

    public String uploadResume(MultipartFile file) {
        try {
            // Get the current user
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate filename using user ID and original extension
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String filename = user.getId() + "." + extension;

            // Save the file
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            return "Resume uploaded successfully: " + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload resume: " + e.getMessage());
        }
    }

    public Resource downloadResume(Long userId) {
        try {
            // Get all files in the resumes directory
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                throw new RuntimeException("Resumes directory not found");
            }

            // Find the file that starts with the userId
            Path resumeFile = Files.list(uploadPath)
                    .filter(path -> path.getFileName().toString().startsWith(userId + "."))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Resume not found for user: " + userId));

            // Load the file as a resource
            Resource resource = new UrlResource(resumeFile.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
} 