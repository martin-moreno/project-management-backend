package co.spa.projectmanagement.security.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // Debe coincidir con los valores del enum Role
}