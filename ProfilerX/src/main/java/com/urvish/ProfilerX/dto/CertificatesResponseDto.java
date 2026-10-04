package com.urvish.ProfilerX.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CertificatesResponseDto {

    private Long certificationsId;

    private List<CertificateDTO> allCertificates;

}
