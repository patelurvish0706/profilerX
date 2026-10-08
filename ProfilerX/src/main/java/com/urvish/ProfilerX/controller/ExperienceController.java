package com.urvish.ProfilerX.controller;

import com.urvish.ProfilerX.dto.ExperienceDTO;
import com.urvish.ProfilerX.dto.ExperienceResponseDto;
import com.urvish.ProfilerX.service.ExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/experience")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping("/{developerId}")
    public ExperienceResponseDto getExperience(@PathVariable Long developerId) {

        return experienceService.getExperience(developerId);

    }

    @PostMapping("/{developerId}")
    public ExperienceResponseDto createExperience(@PathVariable Long developerId, @Valid @RequestBody List<ExperienceDTO> experiences) {

        return experienceService.createExperience(developerId, experiences);

    }

    @PutMapping("/{developerId}")
    public ExperienceResponseDto updateExperience(@PathVariable Long developerId, @Valid @RequestBody List<ExperienceDTO> experiences) {

        return experienceService.updateExperience(developerId, experiences);

    }

    @DeleteMapping("/{developerId}")
    public ResponseEntity<String> deleteExperience(@PathVariable Long developerId) {

        experienceService.deleteExperience(developerId);

        return new ResponseEntity<>("Experience Deleted Successfully", HttpStatus.NO_CONTENT);

    }
}
