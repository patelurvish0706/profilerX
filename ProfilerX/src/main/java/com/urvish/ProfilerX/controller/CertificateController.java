package com.urvish.ProfilerX.controller;

import com.urvish.ProfilerX.dto.CertificateDTO;
import com.urvish.ProfilerX.dto.CertificatesResponseDto;
import com.urvish.ProfilerX.dto.SkillResponseDto;
import com.urvish.ProfilerX.dto.SkillsDTO;
import com.urvish.ProfilerX.service.CertificatesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/certificate")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificatesService certificatesService;

    @GetMapping("/{developerId}")
    public CertificatesResponseDto getCertificates(@PathVariable Long developerId) {

        return certificatesService.getCertificates(developerId);

    }

    @PostMapping("/{developerId}")
    public CertificatesResponseDto createCertificates(@PathVariable Long developerId,@Valid @RequestBody List<CertificateDTO> certificateDTOS) {

        return certificatesService.createCertificates(developerId,certificateDTOS);

    }

    @PutMapping("/{developerId}")
    public CertificatesResponseDto updateCertificates(@PathVariable Long developerId,@Valid @RequestBody List<CertificateDTO> certificateDTOS) {

        return certificatesService.updateCertificates(developerId,certificateDTOS);

    }

    @DeleteMapping("/{developerId}")
    public ResponseEntity<String> deleteCertificates(@PathVariable Long developerId) {

        certificatesService.deleteCertificates(developerId);

        return new ResponseEntity<>("Certificates Deleted Successfully",HttpStatus.NO_CONTENT);

    }
}