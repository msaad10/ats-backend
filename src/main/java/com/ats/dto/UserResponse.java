package com.ats.dto;

import com.ats.model.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
} 