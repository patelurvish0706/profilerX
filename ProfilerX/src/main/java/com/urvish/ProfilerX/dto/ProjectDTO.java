package com.urvish.ProfilerX.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class ProjectDTO {

    @NotBlank(message = "Project name is required")
    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    private String projName;

    @NotBlank(message = "Project description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @NotBlank(message = "Problem solved is required")
    @Size(min = 10, max = 1000, message = "Problem solved must be between 10 and 1000 characters")
    private String probSolved;

    @NotNull(message = "Tools and technologies are required")
    @Size(min = 1, max = 20, message = "Add between 1 and 20 technologies")
    private ArrayList<@NotBlank(message = "Technology cannot be blank") String> toolsTech;

    @NotNull(message = "Project start date is required")
    @PastOrPresent(message = "Project start date cannot be in the future")
    private LocalDate projStartDate;

    @NotNull(message = "Project completion date is required")
    @PastOrPresent(message = "Project completion date cannot be in the future")
    private LocalDate projCompDate;
}