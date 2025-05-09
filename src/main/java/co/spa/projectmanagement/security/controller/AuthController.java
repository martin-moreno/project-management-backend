package co.spa.projectmanagement.security.controller;

import co.spa.projectmanagement.security.dto.AuthRequest;
import co.spa.projectmanagement.security.dto.AuthResponse;
import co.spa.projectmanagement.security.dto.RegisterRequest;
import co.spa.projectmanagement.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Permitir solo el frontend en localhost:3000
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
