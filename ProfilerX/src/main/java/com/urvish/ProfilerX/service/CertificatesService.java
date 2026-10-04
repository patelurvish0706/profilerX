package com.urvish.ProfilerX.service;

import com.urvish.ProfilerX.dto.CertificateDTO;
import com.urvish.ProfilerX.dto.CertificatesResponseDto;
import com.urvish.ProfilerX.dto.SkillResponseDto;
import com.urvish.ProfilerX.dto.SkillsDTO;
import com.urvish.ProfilerX.entity.Certificates;
import com.urvish.ProfilerX.entity.Developer;
import com.urvish.ProfilerX.entity.Skills;
import com.urvish.ProfilerX.repository.CertificatesRepository;
import com.urvish.ProfilerX.repository.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificatesService {

    private final CertificatesRepository certificatesRepository;
    private final DeveloperRepository developerRepository;
    private final DeveloperService developerService;

    public CertificatesResponseDto getCertificates(Long developerId) {

        Certificates existCertificates = certificatesRepository.findByDeveloperId(developerId).orElseThrow(() ->
                        new RuntimeException("Certificates not found"));

        CertificatesResponseDto certiesDto = new CertificatesResponseDto();

        certiesDto.setAllCertificates(existCertificates.getAllCertificates());
        certiesDto.setCertificationsId(existCertificates.getCertificationsId());

        return certiesDto;

    }

    public CertificatesResponseDto createCertificates(Long developerId, List<CertificateDTO> certificates) {

        Developer developer = developerRepository.findById(developerId).orElseThrow(() ->
                        new RuntimeException("Developer not found"));

        if (certificatesRepository.findByDeveloperId(developerId).isPresent()) {
            throw new RuntimeException(
                    "Certificates already exist for this developer");
        }

        Certificates certiEntity = new Certificates();

        certiEntity.setDeveloper(developer);
        certiEntity.setAllCertificates(certificates);

        certiEntity = certificatesRepository.save(certiEntity);

        developerService.update_timeStamp_updation(developerId);

        CertificatesResponseDto certiDto = new CertificatesResponseDto();

        certiDto.setAllCertificates(certiEntity.getAllCertificates());
        certiDto.setCertificationsId(certiEntity.getCertificationsId());

        return certiDto;
    }

    public CertificatesResponseDto updateCertificates(Long developerId, List<CertificateDTO> certificateDTOS) {

        Certificates existing = certificatesRepository.findByDeveloperId(developerId).orElseThrow(() ->
                        new RuntimeException("Certificates not found"));

        existing.setAllCertificates(certificateDTOS);

        existing = certificatesRepository.save(existing);

        developerService.update_timeStamp_updation(developerId);

        CertificatesResponseDto certificatesDto = new CertificatesResponseDto();

        certificatesDto.setAllCertificates(existing.getAllCertificates());
        certificatesDto.setCertificationsId(existing.getCertificationsId());

        return certificatesDto;

    }

    public void deleteCertificates(Long developerId) {

        Certificates existing = certificatesRepository.findByDeveloperId(developerId).orElseThrow(() ->
                        new RuntimeException("Certificates not found"));

        certificatesRepository.delete(existing);
    }
}