package com.urvish.ProfilerX.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExperienceResponseDto {

    private Long experienceId;

    private List<ExperienceDTO> allExperience;

}
