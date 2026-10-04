package com.urvish.ProfilerX.repository;

import com.urvish.ProfilerX.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Long> {

    Optional<Projects> findByDeveloperId(Long developerId);

    void deleteByDeveloperId(Long developerId);

}
