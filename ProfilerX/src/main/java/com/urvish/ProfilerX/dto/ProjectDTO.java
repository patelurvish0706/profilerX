package com.urvish.ProfilerX.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class ProjectDTO {

    private String projName;
    private String description;
    private String probSolved;
    private ArrayList<String> toolsTech;
    private LocalDate projStartDate;
    private LocalDate projCompDate;

}
