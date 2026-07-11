package com.urvish.ProfilerX.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "developer_id", referencedColumnName = "id")
    @JsonIgnore
    private Developer developer;

    private Boolean visibility;
    
    private String themeColor;
    
    private String backgroundColor;
    
    @Column(columnDefinition = "TEXT")
    private String about;
    
    private String resumeUrl;
    
    private Integer profileViews;

    @Column(columnDefinition = "TEXT")
    private String sectionOrder;
}
