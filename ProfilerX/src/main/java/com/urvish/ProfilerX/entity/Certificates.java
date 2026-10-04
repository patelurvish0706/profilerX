package com.urvish.ProfilerX.entity;

import com.urvish.ProfilerX.dto.CertificateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "Certificates")
public class Certificates {

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationsId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<CertificateDTO> allCertificates = new ArrayList<>();

}
