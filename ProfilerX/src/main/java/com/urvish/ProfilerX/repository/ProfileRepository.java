package com.urvish.ProfilerX.repository;

import com.urvish.ProfilerX.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByDeveloperId(Long developerId);
}
