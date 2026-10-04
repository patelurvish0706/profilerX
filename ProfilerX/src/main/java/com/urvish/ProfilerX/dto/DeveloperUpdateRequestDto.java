package com.urvish.ProfilerX.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DeveloperUpdateRequestDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

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
