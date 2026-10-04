package com.urvish.ProfilerX.entity;

import com.urvish.ProfilerX.dto.ProjectDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Data
@Table(name = "Projects")
public class Projects {

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private ArrayList<ProjectDTO> allProjects;

}
