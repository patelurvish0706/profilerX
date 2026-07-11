package com.urvish.ProfilerX.service;

import com.urvish.ProfilerX.dto.AuthRequest;
import com.urvish.ProfilerX.dto.AuthResponse;
import com.urvish.ProfilerX.dto.RegisterRequest;
import com.urvish.ProfilerX.entity.Developer;
import com.urvish.ProfilerX.entity.Profile;
import com.urvish.ProfilerX.repository.DeveloperRepository;
import com.urvish.ProfilerX.repository.ProfileRepository;
import com.urvish.ProfilerX.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final DeveloperRepository developerRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (developerRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (developerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Developer developer = Developer.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .emailVerified(false)
                .phoneVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Developer savedDeveloper = developerRepository.save(developer);

        // Create empty profile
        Profile profile = Profile.builder()
                .developer(savedDeveloper)
                .visibility(true)
                .profileViews(0)
                .themeColor("#000000") // default
                .backgroundColor("#ffffff") // default
                .build();
        profileRepository.save(profile);

        String jwtToken = jwtUtils.generateToken(savedDeveloper.getUsername());
        
        savedDeveloper.setJwtToken(jwtToken);
        savedDeveloper.setJwtValid(true);
        savedDeveloper.setJwtExpiry(LocalDateTime.now().plusHours(10));
        developerRepository.save(savedDeveloper);

        return AuthResponse.builder()
                .token(jwtToken)
                .username(savedDeveloper.getUsername())
                .message("Developer registered successfully")
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Developer developer = developerRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtUtils.generateToken(developer.getUsername());

        developer.setJwtToken(jwtToken);
        developer.setJwtValid(true);
        developer.setJwtExpiry(LocalDateTime.now().plusHours(10));
        developerRepository.save(developer);

        return AuthResponse.builder()
                .token(jwtToken)
                .username(developer.getUsername())
                .message("Login successful")
                .build();
    }
}
