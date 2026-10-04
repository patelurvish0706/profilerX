package com.urvish.ProfilerX.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SkillsDTO {

    @NotBlank(message = "Skill category is required")
    private String category;

    @NotEmpty(message = "Skills are required")
    private List<@NotBlank(message = "Skill name cannot be blank") String> skills;

}
