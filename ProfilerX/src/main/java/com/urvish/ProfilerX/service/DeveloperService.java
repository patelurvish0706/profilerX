package com.urvish.ProfilerX.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.urvish.ProfilerX.entity.Developer;
import com.urvish.ProfilerX.repository.DeveloperRepository;

@Service
@RequiredArgsConstructor
public class DeveloperService {
    
    private final DeveloperRepository developerRepository;

    public Developer registerDeveloper(Developer developer) {
        return developerRepository.save(developer);
    }

}
