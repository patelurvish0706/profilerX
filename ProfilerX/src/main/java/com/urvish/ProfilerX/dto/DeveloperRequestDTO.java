package com.urvish.ProfilerX.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class DeveloperRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+$",
            message = "Username can contain only letters, numbers and underscore"
    )
    private String username;

    @NotNull(message = "Phone number is required")
    @Positive(message = "Phone number must be positive")
    private Long phone;

    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    @Size(max = 100, message = "Occupation cannot exceed 100 characters")
    private String occupation;

    @Size(max = 300, message = "Brief about cannot exceed 300 characters")
    private String briefAbout;

    @Size(max = 2000, message = "Introduction cannot exceed 2000 characters")
    private String introduction;

    private boolean publicProfile;

    @Pattern(
            regexp = "^#[0-9A-Fa-f]{6}$",
            message = "Theme color must be a valid HEX color, e.g. #ffffff"
    )
    private String themeColor;

    @Size(max = 300, message = "Currently working on cannot exceed 300 characters")
    private String currentlyWorkingOn;
}