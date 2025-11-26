package com.example.job_service.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role; // We need to check if this is "CLIENT"
}