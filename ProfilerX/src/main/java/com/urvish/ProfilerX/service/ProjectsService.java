package com.urvish.ProfilerX.service;

import com.urvish.ProfilerX.dto.ProjectDTO;
import com.urvish.ProfilerX.dto.ProjectsResponseDto;
import com.urvish.ProfilerX.entity.Developer;
import com.urvish.ProfilerX.entity.Projects;
import com.urvish.ProfilerX.repository.DeveloperRepository;
import com.urvish.ProfilerX.repository.ProjectsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectsService {

    private final ProjectsRepository projectsRepository;
    private final DeveloperRepository developerRepository;
    private final DeveloperService developerService;

    public ProjectsResponseDto getProjects(Long developerId) {
        Projects existProjects = projectsRepository.findByDeveloperId(developerId).orElseThrow(() ->
                new RuntimeException("Projects not found"));

        ProjectsResponseDto projectsDto = new ProjectsResponseDto();

        projectsDto.setAllProjects(existProjects.getAllProjects());
        projectsDto.setProjectId(existProjects.getProjectId());

        return projectsDto;
    }

    public ProjectsResponseDto createProjects(Long developerId, @Valid List<ProjectDTO> projects) {
        Developer developer = developerRepository.findById(developerId).orElseThrow(() ->
                new RuntimeException("Developer not found"));

        if (projectsRepository.findByDeveloperId(developerId).isPresent()) {
            throw new RuntimeException(
                    "Projects already exist for this developer");
        }

        Projects projectsEntity = new Projects();

        projectsEntity.setDeveloper(developer);
        projectsEntity.setAllProjects(projects);

        projectsEntity = projectsRepository.save(projectsEntity);

        developerService.update_timeStamp_updation(developerId);

        ProjectsResponseDto projectsDto = new ProjectsResponseDto();

        projectsDto.setAllProjects(projectsEntity.getAllProjects());
        projectsDto.setProjectId(projectsEntity.getProjectId());

        return projectsDto;
    }

    public ProjectsResponseDto updateProjects(Long developerId, @Valid List<ProjectDTO> projects) {

        Projects projectsEntity = projectsRepository.findByDeveloperId(developerId).orElseThrow(() ->
                new RuntimeException("Projects not found"));

        projectsEntity.setAllProjects(projects);

        projectsEntity = projectsRepository.save(projectsEntity);

        developerService.update_timeStamp_updation(developerId);

        ProjectsResponseDto projectsDto = new ProjectsResponseDto();

        projectsDto.setAllProjects(projectsEntity.getAllProjects());
        projectsDto.setProjectId(projectsEntity.getProjectId());

        return projectsDto;
    }

    public void deleteProjects(Long developerId) {

        Projects existing = projectsRepository.findByDeveloperId(developerId).orElseThrow(() ->
                new RuntimeException("Projects not found"));

        projectsRepository.delete(existing);
    }
}
