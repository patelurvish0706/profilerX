package com.urvish.ProfilerX.dto;

import lombok.Data;

import java.time.LocalDate;

@Data

public class ExperienceDTO {

    private String companyName;
    private String role;

    private LocalDate joiningDate;
    private LocalDate toDate;

    private String description;

}
