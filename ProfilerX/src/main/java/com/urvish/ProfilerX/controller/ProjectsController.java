package com.urvish.ProfilerX.controller;

import com.urvish.ProfilerX.dto.ProjectDTO;
import com.urvish.ProfilerX.dto.ProjectsResponseDto;
import com.urvish.ProfilerX.service.ProjectsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/projects")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectsService projectsService;

    @GetMapping("/{developerId}")
    public ResponseEntity<?> getProjects(@PathVariable Long developerId) {

    try{
         ProjectsResponseDto projects = projectsService.getProjects(developerId);
        return new ResponseEntity<>(projects,HttpStatus.ACCEPTED);
    }catch (RuntimeException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    }

    @PostMapping("/{developerId}")
    public ProjectsResponseDto createProjects(@PathVariable Long developerId,@RequestBody List<@Valid ProjectDTO> projects) {

        return projectsService.createProjects(developerId, projects);

    }

    @PutMapping("/{developerId}")
    public ProjectsResponseDto updateProjects(@PathVariable Long developerId,@RequestBody List<@Valid ProjectDTO> projects) {

        return projectsService.updateProjects(developerId, projects);

    }

    @DeleteMapping("/{developerId}")
    public ResponseEntity<String> deleteProjects(@PathVariable Long developerId) {

        projectsService.deleteProjects(developerId);

        return new ResponseEntity<>("Projects Deleted Successfully", HttpStatus.NO_CONTENT);

    }
}
