package com.urvish.ProfilerX.dto;

import lombok.Data;

import java.util.List;


@Data
public class SkillResponseDto {

    private Long skillsId;

    private List<SkillsDTO> allSkills;

}
