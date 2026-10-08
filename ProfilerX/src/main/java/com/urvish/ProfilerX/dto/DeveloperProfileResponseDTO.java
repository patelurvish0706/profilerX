package com.urvish.ProfilerX.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class DeveloperProfileResponseDTO {

    private DeveloperResponseDTO developer;

    private Optional<BlogsResponseDto> blogs;
    private Optional<CertificatesResponseDto> certificates;
    private Optional<ExperienceResponseDto> experiences;
    private Optional<ProjectsResponseDto> projects;
    private Optional<SkillResponseDto> skills;

    private boolean publicProfile = true;

}
