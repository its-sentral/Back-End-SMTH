package com.example.AAMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.AAMS.repository.DynamicAuthRepository;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private DynamicAuthRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public String authenticateAndGenerateToken(String username, String rawPassword) {
        try {
            // Retrieve hashed password from dynamic table
            String hashedStoredPassword = repository.getPassword(username);
            
            // Validate password
            if (passwordEncoder.matches(rawPassword, hashedStoredPassword)) {
                //  Generate unique token
                String token = UUID.randomUUID().toString();
                
                // Save token to database
                repository.saveToken(username, token);
                
                return token;
            }
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed: Invalid username or password");
        }
        throw new RuntimeException("Invalid Credentials");
    }

    //  Check authorization
    public Integer getAuthorizationKey(String username) {
        // This returns the Foreign Key from the auth table
        return repository.getAuthId(username);
    }

    public void registerUser(String username, String rawPassword) {
        // Hash the password 
        String hashedPassword = passwordEncoder.encode(rawPassword);
        
        // Save to the dynamic user table
        repository.saveUser(username, hashedPassword);
    }
}