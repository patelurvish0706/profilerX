package com.urvish.ProfilerX.repository;

import com.urvish.ProfilerX.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long> {

    Optional<Skills> findByDeveloperId(Long developerId);

    void deleteByDeveloperId(Long developerId);
}