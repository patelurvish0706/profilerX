package com.urvish.ProfilerX.service;

import com.urvish.ProfilerX.dto.ExperienceDTO;
import com.urvish.ProfilerX.dto.ExperienceResponseDto;
import com.urvish.ProfilerX.entity.Developer;
import com.urvish.ProfilerX.entity.Experience;
import com.urvish.ProfilerX.repository.DeveloperRepository;
import com.urvish.ProfilerX.repository.ExperienceRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final DeveloperRepository developerRepository;
    private final DeveloperService developerService;


    public ExperienceResponseDto getExperience(Long developerId) {

        Experience existExp = experienceRepository.findByDeveloperId(developerId).orElseThrow(() ->
                new RuntimeException("Experience not found"));

        ExperienceResponseDto experienceResponseDto = new ExperienceResponseDto();

        experienceResponseDto.setAllExperience(existExp.getAllExperience());
        experienceResponseDto.setExperienceId(existExp.getExperienceId());

        return experienceResponseDto;

    }

    public ExperienceResponseDto createExperience(Long developerId, @Valid List<ExperienceDTO> experiences) {

        Developer developer = developerRepository.findById(developerId).orElseThrow(() ->
                new RuntimeException("Developer not found"));

        if (experienceRepository.findByDeveloperId(developerId).isPresent()) {
            throw new RuntimeException(
                    "Experience already exist for this developer");
        }

        Experience experienceEntity  = new Experience();

        experienceEntity.setDeveloper(developer);
        experienceEntity.setAllExperience(experiences);

        experienceEntity = experienceRepository.save(experienceEntity);

        developerService.update_timeStamp_updation(developerId);

        ExperienceResponseDto experienceResponseDto = new ExperienceResponseDto();

        experienceResponseDto.setAllExperience(experienceEntity.getAllExperience());
        experienceResponseDto.setExperienceId(experienceEntity.getExperienceId());

        return experienceResponseDto;
    }

    public ExperienceResponseDto updateExperience(Long developerId, @Valid List<ExperienceDTO> experiences) {

        Experience existingExp = experienceRepository.findByDeveloperId(developerId).orElseThrow(() ->
                new RuntimeException("Experience not found"));

        existingExp.setAllExperience(experiences);

        existingExp = experienceRepository.save(existingExp);

        developerService.update_timeStamp_updation(developerId);

        ExperienceResponseDto experienceResponseDto = new ExperienceResponseDto();

        experienceResponseDto.setAllExperience(existingExp.getAllExperience());
        experienceResponseDto.setExperienceId(existingExp.getExperienceId());

        return experienceResponseDto;

    }

    public void deleteExperience(Long developerId) {
        Experience existing = experienceRepository.findByDeveloperId(developerId).orElseThrow(() ->
                new RuntimeException("Skills not found"));

        experienceRepository.delete(existing);
    }
}
