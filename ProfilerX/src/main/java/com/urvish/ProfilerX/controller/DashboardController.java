package com.urvish.ProfilerX.controller;

import com.urvish.ProfilerX.entity.*;
import com.urvish.ProfilerX.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DeveloperRepository developerRepository;
    private final ProfileRepository profileRepository;

    private Developer getAuthenticatedDeveloper() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return developerRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Developer not found"));

        }
        throw new RuntimeException("Not authenticated");
    }

    @GetMapping
    public ResponseEntity<?> getDashboardData() {
        Developer developer = getAuthenticatedDeveloper();
        Profile profile = profileRepository.findByDeveloperId(developer.getId()).orElse(null);


        Map<String, Object> response = new HashMap<>();
//         response.put("developer", developer);
         response.put("profile", profile);


        // Include other sections later if needed
        return ResponseEntity.ok(response);
    }

    @PutMapping("/customize")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> updates) {
        Developer developer = getAuthenticatedDeveloper();
        Profile profile = profileRepository.findByDeveloperId(developer.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (updates.containsKey("visibility")) {
            profile.setVisibility((Boolean) updates.get("visibility"));
        }
        if (updates.containsKey("themeColor")) {
            profile.setThemeColor((String) updates.get("themeColor"));
        }
        if (updates.containsKey("backgroundColor")) {
            profile.setBackgroundColor((String) updates.get("backgroundColor"));
        }
        if (updates.containsKey("about")) {
            profile.setAbout((String) updates.get("about"));
        }
        if (updates.containsKey("sectionOrder")) {
            profile.setSectionOrder((String) updates.get("sectionOrder"));
        }

        profileRepository.save(profile);
        return ResponseEntity.ok(profile);
    }
}
