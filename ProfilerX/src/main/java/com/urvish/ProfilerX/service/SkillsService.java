package com.urvish.ProfilerX.service;

import com.urvish.ProfilerX.dto.SkillResponseDto;
import com.urvish.ProfilerX.dto.SkillsDTO;
import com.urvish.ProfilerX.entity.Developer;
import com.urvish.ProfilerX.entity.Skills;
import com.urvish.ProfilerX.repository.DeveloperRepository;
import com.urvish.ProfilerX.repository.SkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillsService {

    private final SkillsRepository skillsRepository;
    private final DeveloperRepository developerRepository;
    private final DeveloperService developerService;

    public SkillResponseDto getSkills(Long developerId) {

        Skills existSkills = skillsRepository.findByDeveloperId(developerId).orElse(null);

        SkillResponseDto skillDto = new SkillResponseDto();

        skillDto.setAllSkills(existSkills.getAllSkills());
        skillDto.setSkillsId(existSkills.getSkillsId());

        return skillDto;

    }

    public SkillResponseDto createSkills(Long developerId, List<SkillsDTO> skills) {

        Developer developer = developerRepository.findById(developerId).orElseThrow(() ->
                        new RuntimeException("Developer not found"));

        if (skillsRepository.findByDeveloperId(developerId).isPresent()) {
            throw new RuntimeException(
                    "Skills already exist for this developer");
        }

        Skills skillsEntity = new Skills();

        skillsEntity.setDeveloper(developer);
        skillsEntity.setAllSkills(skills);

        Skills savedskill = skillsRepository.save(skillsEntity);

        developerService.update_timeStamp_updation(developerId);

        SkillResponseDto skillDto = new SkillResponseDto();

        skillDto.setAllSkills(savedskill.getAllSkills());
        skillDto.setSkillsId(savedskill.getSkillsId());

        return skillDto;
    }

    public SkillResponseDto updateSkills(Long developerId, List<SkillsDTO> skills) {

        Skills existing = skillsRepository.findByDeveloperId(developerId).orElseThrow(() ->
                        new RuntimeException("Skills not found"));

        existing.setAllSkills(skills);

        Skills savedskill = skillsRepository.save(existing);

        developerService.update_timeStamp_updation(developerId);

        SkillResponseDto skillDto = new SkillResponseDto();

        skillDto.setAllSkills(savedskill.getAllSkills());
        skillDto.setSkillsId(savedskill.getSkillsId());

        return skillDto;

    }

    public void deleteSkills(Long developerId) {

        Skills existing = skillsRepository.findByDeveloperId(developerId).orElseThrow(() ->
                        new RuntimeException("Skills not found"));

        skillsRepository.delete(existing);
    }
}