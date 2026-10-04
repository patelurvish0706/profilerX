package com.urvish.ProfilerX.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CertificateDTO {

    @NotBlank(message = "Certificate name is required")
    private String certificateName;

    @NotBlank(message = "Issuer name is required")
    private String issuer;

    @NotNull(message = "Issue Date is Required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate issueDate;


}
