package com.ats.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "scheduled_interview")
public class ScheduledInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_candidate_id", nullable = false)
    private JobCandidate jobCandidate;

    @ManyToOne
    @JoinColumn(name = "interviewer_id", nullable = false)
    private User interviewer;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InterviewType interviewType;

    @Column
    @Enumerated(EnumType.STRING)
    private InterviewResult result;

    @Column(columnDefinition = "TEXT")
    private String feedback;
} 