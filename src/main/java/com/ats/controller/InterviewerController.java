package com.ats.controller;

import com.ats.dto.UserResponse;
import com.ats.mapper.UserMapper;
import com.ats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interviewers")
public class InterviewerController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('RECRUITER', 'INTERVIEWER')")
    public ResponseEntity<List<UserResponse>> getAllInterviewers() {
        return ResponseEntity.ok(userService.getUsersByRole("INTERVIEWER")
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList()));
    }
} 