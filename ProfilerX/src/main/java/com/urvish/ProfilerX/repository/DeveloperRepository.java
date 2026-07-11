package com.urvish.ProfilerX.repository;
import com.urvish.ProfilerX.entity.Developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    java.util.Optional<Developer> findByUsername(String username);
    java.util.Optional<Developer> findByEmail(String email);

}