package com.urvish.ProfilerX.dto;

import com.urvish.ProfilerX.entity.Developer;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeveloperResponseDTO {

    private Long id;

    private String name;

    private String email;

//    private String password;

    private String username;

    private Long phone;

    private String location;

    private String occupation;

    private String briefAbout;

    private String introduction;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean publicProfile;

    private String themeColor;

    private String currentlyWorkingOn;

}

