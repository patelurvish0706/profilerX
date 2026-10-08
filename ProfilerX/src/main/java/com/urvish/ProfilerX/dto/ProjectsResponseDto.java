package com.urvish.ProfilerX.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectsResponseDto {

    private Long projectId;

    private List<ProjectDTO> allProjects;

}
