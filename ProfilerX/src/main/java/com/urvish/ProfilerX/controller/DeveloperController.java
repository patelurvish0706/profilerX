package com.urvish.ProfilerX.controller;

import com.urvish.ProfilerX.dto.DeveloperRequestDTO;
import com.urvish.ProfilerX.dto.DeveloperResponseDTO;
import com.urvish.ProfilerX.dto.DeveloperUpdateRequestDto;
import com.urvish.ProfilerX.service.DeveloperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/profile")
public class DeveloperController {

    private final DeveloperService developerService;

    @GetMapping()
    public ResponseEntity<?> allDevs(){
        List<DeveloperResponseDTO> allDevelopersResponse = developerService.showAllDevs();
        if(allDevelopersResponse.isEmpty()){
            return new ResponseEntity<>("No Users Found", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(allDevelopersResponse, HttpStatus.OK);
    }

    @PostMapping
    public DeveloperResponseDTO storeDeveloper(@Valid @RequestBody DeveloperRequestDTO developer){
        return developerService.newDeveloper(developer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeveloper(@PathVariable Long id){
        DeveloperResponseDTO existDev = developerService.getSpecificDeveloper(id);

        if (existDev == null){
            return new ResponseEntity<>("No Developer Found" , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(existDev, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeveloper(@PathVariable Long id, @Valid @RequestBody DeveloperUpdateRequestDto developerUpdateRequestDto){

        DeveloperResponseDTO existDev = developerService.updateSpecificDeveloper(id,developerUpdateRequestDto);
        if(existDev == null){
            return new ResponseEntity<>("Developer not Found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(existDev,HttpStatus.ACCEPTED);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeveloper(@PathVariable Long id,@RequestBody String password){
        return developerService.deleteDeveloperProfile(id,password);
    }

}
