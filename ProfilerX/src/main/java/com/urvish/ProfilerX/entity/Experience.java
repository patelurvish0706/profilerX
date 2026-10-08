package com.urvish.ProfilerX.entity;

import com.urvish.ProfilerX.dto.ExperienceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "Experiences")
public class Experience {

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long experienceId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<ExperienceDTO> allExperience = new ArrayList<>();

}
