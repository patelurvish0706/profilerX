package com.urvish.ProfilerX.controller;

import com.urvish.ProfilerX.dto.DeveloperProfileResponseDTO;
import com.urvish.ProfilerX.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final DeveloperService developerService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getProfile(@PathVariable String username){

        try {
            DeveloperProfileResponseDTO devDetails = developerService.getProfile(username);

            return new ResponseEntity<>(devDetails, HttpStatus.OK);

        }
        catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage()+": No Developer Profiles Exist", HttpStatus.NOT_FOUND);
        }




    }

}
