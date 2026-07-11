package com.urvish.ProfilerX.controller;

import com.urvish.ProfilerX.entity.*;
import com.urvish.ProfilerX.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class PortfolioController {

    private final DeveloperRepository developerRepository;
    private final ProfileRepository profileRepository;
    private final LinkRepository linkRepository;
    private final SkillRepository skillRepository;
    private final ExperienceRepository experienceRepository;
    private final ProjectRepository projectRepository;
    private final CertificationRepository certificationRepository;
    private final CurrentlyDoingRepository currentlyDoingRepository;
    private final BlogRepository blogRepository;

    @GetMapping("/{username}")
    public ResponseEntity<?> getPublicProfile(@PathVariable String username) {
        Developer developer = developerRepository.findByUsername(username).orElse(null);
        if (developer == null) {
            return ResponseEntity.notFound().build();
        }

        Profile profile = profileRepository.findByDeveloperId(developer.getId()).orElse(null);
        if (profile == null || !Boolean.TRUE.equals(profile.getVisibility())) {
            return ResponseEntity.status(403).body("Profile is private or does not exist.");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("developer", Map.of(
                "fullName", developer.getFullName(),
                "email", developer.getEmail(),
                "phone", developer.getPhone()));
        response.put("profile", profile);
        response.put("links", linkRepository.findByDeveloperId(developer.getId()));
        response.put("skills", skillRepository.findByDeveloperId(developer.getId()));
        response.put("experience", experienceRepository.findByDeveloperId(developer.getId()));
        response.put("projects", projectRepository.findByDeveloperId(developer.getId()));
        response.put("certifications", certificationRepository.findByDeveloperId(developer.getId()));
        response.put("currentlyDoing", currentlyDoingRepository.findByDeveloperId(developer.getId()));
        response.put("blogs", blogRepository.findByDeveloperId(developer.getId()));

        return ResponseEntity.ok(response);
    }
}
