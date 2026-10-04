package com.urvish.ProfilerX.controller;

import com.urvish.ProfilerX.dto.SkillResponseDto;
import com.urvish.ProfilerX.dto.SkillsDTO;
import com.urvish.ProfilerX.service.SkillsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/skills")
@RequiredArgsConstructor
public class SkillsController {

    private final SkillsService skillsService;

    @GetMapping("/{developerId}")
    public SkillResponseDto getSkills(@PathVariable Long developerId) {

        return skillsService.getSkills(developerId);

    }

    @PostMapping("/{developerId}")
    public SkillResponseDto createSkills(@PathVariable Long developerId,@Valid @RequestBody List<SkillsDTO> skills) {

        return skillsService.createSkills(developerId,skills);

    }

    @PutMapping("/{developerId}")
    public SkillResponseDto updateSkills(@PathVariable Long developerId,@Valid @RequestBody List<SkillsDTO> skills) {

        return skillsService.updateSkills(developerId,skills);

    }

    @DeleteMapping("/{developerId}")
    public ResponseEntity<String> deleteSkills(@PathVariable Long developerId) {

        skillsService.deleteSkills(developerId);

        return new ResponseEntity<>("Skill Deleted Successfully",HttpStatus.NO_CONTENT);

    }
}