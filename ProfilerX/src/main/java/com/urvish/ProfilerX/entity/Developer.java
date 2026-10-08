package com.urvish.ProfilerX.entity;

import com.urvish.ProfilerX.dto.DeveloperResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Developers")
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String name;


    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(nullable = false, unique = true)
    private String email;


    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;


    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30,
            message = "Username must be between 3 and 30 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+$",
            message = "Username can contain only letters, numbers and underscores"
    )
    @Column(nullable = false, unique = true)
    private String username;


    @NotNull(message = "Phone number is required")
    @Digits(integer = 10, fraction = 0,
            message = "Phone number must contain exactly 10 digits")
    @Positive(message = "Phone number must be valid")
    @Column(nullable = false)
    private Long phone;


    @NotBlank(message = "Location is required")
    @Size(max = 100, message = "Location must not exceed 100 characters")
    private String location;


    @NotBlank(message = "Occupation is required")
    @Size(min = 2, max = 100,
            message = "Occupation must be between 2 and 100 characters")
    private String occupation;


    @NotBlank(message = "Brief about is required")
    @Size(min = 10, max = 500,
            message = "Brief about must be between 10 and 500 characters")
    private String briefAbout;


    @NotBlank(message = "Introduction is required")
    @Size(min = 10, max = 1000,
            message = "Introduction must be between 10 and 1000 characters")
    private String introduction;


    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Must Select Profile Status (Public/Private)")
    private boolean publicProfile ;


    @NotBlank(message = "Theme color is required")
    @Pattern(
            regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$",
            message = "Theme color must be a valid HEX color"
    )
    private String themeColor;

    @NotBlank(message = "Currently working objective is required")
    @Size(max = 200, message = "Currently working on must not exceed 200 characters")
    private String currentlyWorkingOn;

}

