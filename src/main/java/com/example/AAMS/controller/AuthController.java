package com.example.AAMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.AAMS.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint to Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        try {
            String token = authService.authenticateAndGenerateToken(username, password);
            return ResponseEntity.ok(Map.of("token", token, "message", "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // Endpoint to get Authorization
    @GetMapping("/authorize")
    public ResponseEntity<?> authorize(@RequestParam String username) {
        try {
            Integer authId = authService.getAuthorizationKey(username);
            return ResponseEntity.ok(Map.of("authorization_fk", authId));
        } catch (Exception e) {
            return ResponseEntity.status(403).body("User not authorized");
        }
    }

    // Add this to your AuthController class
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            
            authService.registerUser(username, password);
            
            return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }
}