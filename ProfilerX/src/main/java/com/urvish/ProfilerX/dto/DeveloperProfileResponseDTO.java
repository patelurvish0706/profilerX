package com.urvish.ProfilerX.dto;

import com.urvish.ProfilerX.entity.*;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class DeveloperProfileResponseDTO {

    private DeveloperResponseDTO developer;

    private Optional<Blogs> blogs;
    private Optional<CertificatesResponseDto> certificates;
    private Optional<Experience> experiences;
    private Optional<Projects> projects;
    private Optional<SkillResponseDto> skills;

}
