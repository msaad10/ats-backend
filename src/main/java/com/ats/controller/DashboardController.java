package com.ats.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    @GetMapping
    public ResponseEntity<?> getDashboardData(Authentication authentication) {
        // This is a protected endpoint that requires authentication
        // The authentication object contains the current user's details
        return ResponseEntity.ok(Map.of(
            "message", "Welcome to the dashboard!",
            "user", authentication.getName(),
            "authorities", authentication.getAuthorities()
        ));
    }
} 