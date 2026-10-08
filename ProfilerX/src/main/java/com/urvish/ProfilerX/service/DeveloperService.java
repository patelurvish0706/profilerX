package com.urvish.ProfilerX.service;

import com.urvish.ProfilerX.dto.*;
import com.urvish.ProfilerX.entity.Developer;
import com.urvish.ProfilerX.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final BlogsRepository blogsRepository;
    private final CertificatesRepository certificatesRepository;
    private final ProjectsRepository projectsRepository;
    private final SkillsRepository skillsRepository;
    private final ExperienceRepository experienceRepository;

    public DeveloperResponseDTO entityToDto(Developer developer){

        DeveloperResponseDTO DTO = new DeveloperResponseDTO();

        DTO.setId(developer.getId());
        DTO.setName(developer.getName());
        DTO.setEmail(developer.getEmail());
        DTO.setUsername(developer.getUsername());
        DTO.setPhone(developer.getPhone());
        DTO.setLocation(developer.getLocation());
        DTO.setOccupation(developer.getOccupation());
        DTO.setBriefAbout(developer.getBriefAbout());
        DTO.setIntroduction(developer.getIntroduction());
        DTO.setCreatedAt(developer.getCreatedAt());
        DTO.setUpdatedAt(developer.getUpdatedAt());
        DTO.setPublicProfile(developer.isPublicProfile());
        DTO.setThemeColor(developer.getThemeColor());
        DTO.setCurrentlyWorkingOn(developer.getCurrentlyWorkingOn());

        return DTO;
    }

    public Developer dtoToEntity(DeveloperRequestDTO developer){

        Developer dev = new Developer();

        dev.setName(developer.getName());
        dev.setEmail(developer.getEmail());
        dev.setUsername(developer.getUsername());
        dev.setPassword(developer.getPassword());
        dev.setPhone(developer.getPhone());
        dev.setLocation(developer.getLocation());
        dev.setOccupation(developer.getOccupation());
        dev.setBriefAbout(developer.getBriefAbout());
        dev.setIntroduction(developer.getIntroduction());
        dev.setPublicProfile(developer.isPublicProfile());
        dev.setThemeColor(developer.getThemeColor());
        dev.setCurrentlyWorkingOn(developer.getCurrentlyWorkingOn());

        return dev;
    }

    public Developer dtoToEntityForUpdate(DeveloperUpdateRequestDto developer, Developer existingDev){

//        Developer dev = new Developer();

        existingDev.setName(developer.getName());
        existingDev.setPhone(developer.getPhone());
        existingDev.setLocation(developer.getLocation());
        existingDev.setOccupation(developer.getOccupation());
        existingDev.setBriefAbout(developer.getBriefAbout());
        existingDev.setIntroduction(developer.getIntroduction());
        existingDev.setPublicProfile(developer.isPublicProfile());
        existingDev.setUpdatedAt(LocalDateTime.now());
        existingDev.setThemeColor(developer.getThemeColor());
        existingDev.setCurrentlyWorkingOn(developer.getCurrentlyWorkingOn());

        return existingDev;
    }

    public DeveloperResponseDTO newDeveloper(@Valid DeveloperRequestDTO developerRequestDTO){

            Developer newDev = dtoToEntity(developerRequestDTO);
            Developer savedDev = developerRepository.save(newDev);
            return entityToDto(savedDev);

    }

    public List<DeveloperResponseDTO> showAllDevs(){
        List<Developer> allDevs = developerRepository.findAll();

        if(allDevs.isEmpty()){
            return null;
        }

        List<DeveloperResponseDTO> developersDto = new ArrayList<>(List.of());

        for(Developer dev : allDevs){

            developersDto.add(entityToDto(dev));

        }

        return developersDto;
    }

    public DeveloperResponseDTO getSpecificDeveloper(Long id){
        Developer existingDev = developerRepository.findById(id).orElse(null);

        DeveloperResponseDTO newExistingDev;

        if(existingDev == null){
            return null;
        }else {
            newExistingDev = entityToDto(existingDev);
        }

        return newExistingDev;
    }


    public DeveloperResponseDTO updateSpecificDeveloper(Long id, @Valid DeveloperUpdateRequestDto developerUpdateRequestDto ){
        Developer existingDev = developerRepository.findById(id).orElse(null);

        DeveloperResponseDTO storedUpdated;
        if(existingDev == null){
            return null;
        }else {
            existingDev = dtoToEntityForUpdate(developerUpdateRequestDto,existingDev);
            storedUpdated = entityToDto(developerRepository.save(existingDev));
        }

        return storedUpdated;
    }

    public ResponseEntity<String> deleteDeveloperProfile(Long id,String password) {
        Developer existingDev = developerRepository.findById(id).orElse(null);

        if(existingDev == null){
            return new ResponseEntity<>("Inappropriate Operation declined", HttpStatus.BAD_REQUEST);
        }else {
            if(password.equals(existingDev.getPassword())){
                developerRepository.delete(existingDev);
                return new ResponseEntity<>("Profile Deleted Successfully.", HttpStatus.OK) ;
            }

            return new ResponseEntity<>("Unauthorized Operation!!" , HttpStatus.UNAUTHORIZED);

        }
    }

    public void update_timeStamp_updation(Long id){
        Developer existedDev = developerRepository.findById(id).orElseThrow(null);

        existedDev.setUpdatedAt(LocalDateTime.now());

        developerRepository.save(existedDev);
    }

    //-----------------Profile---------------------

    public DeveloperProfileResponseDTO getProfile(String username) {

        Developer developer = developerRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Developer not found"));

        DeveloperProfileResponseDTO response = new DeveloperProfileResponseDTO();

        // Profile is private
        if (!developer.isPublicProfile()) {
            response.setPublicProfile(false);
            return response;
        }

        response.setPublicProfile(true);

        // Developer
        response.setDeveloper(entityToDto(developer));

        // Blogs
        blogsRepository.findByDeveloperId(developer.getId())
                .ifPresent(blogs -> {

                    BlogsResponseDto dto = new BlogsResponseDto();

                    dto.setBlogsId(blogs.getBlogsId());
                    dto.setAllBlogs(blogs.getAllBlogs());

                    response.setBlogs(Optional.of(dto));
                });

        // Experiences
        experienceRepository.findByDeveloperId(developer.getId())
                .ifPresent(experience -> {

                    ExperienceResponseDto dto = new ExperienceResponseDto();

                    dto.setExperienceId(experience.getExperienceId());
                    dto.setAllExperience(experience.getAllExperience());

                    response.setExperiences(Optional.of(dto));
                });

        // Projects
        projectsRepository.findByDeveloperId(developer.getId())
                .ifPresent(projects -> {

                    ProjectsResponseDto dto = new ProjectsResponseDto();

                    dto.setProjectId(projects.getProjectId());
                    dto.setAllProjects(projects.getAllProjects());

                    response.setProjects(Optional.of(dto));
                });

        // Certificates
        certificatesRepository.findByDeveloperId(developer.getId())
                .ifPresent(certificates -> {

                    CertificatesResponseDto dto = new CertificatesResponseDto();

                    dto.setCertificationsId(certificates.getCertificationsId());
                    dto.setAllCertificates(certificates.getAllCertificates());

                    response.setCertificates(Optional.of(dto));
                });

        // Skills
        skillsRepository.findByDeveloperId(developer.getId())
                .ifPresent(skills -> {

                    SkillResponseDto dto = new SkillResponseDto();

                    dto.setSkillsId(skills.getSkillsId());
                    dto.setAllSkills(skills.getAllSkills());

                    response.setSkills(Optional.of(dto));
                });

        return response;
    }



}

