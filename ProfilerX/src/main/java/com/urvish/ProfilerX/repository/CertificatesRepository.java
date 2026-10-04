package com.urvish.ProfilerX.repository;

import com.urvish.ProfilerX.entity.Certificates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificatesRepository extends JpaRepository<Certificates,Long> {

    Optional<Certificates> findByDeveloperId(Long developerId);

    void deleteByDeveloperId(Long developerId);

}
