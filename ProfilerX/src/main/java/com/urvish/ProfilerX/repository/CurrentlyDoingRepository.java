package com.urvish.ProfilerX.repository;

import com.urvish.ProfilerX.entity.CurrentlyDoing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrentlyDoingRepository extends JpaRepository<CurrentlyDoing, Long> {
    List<CurrentlyDoing> findByDeveloperId(Long developerId);
}
