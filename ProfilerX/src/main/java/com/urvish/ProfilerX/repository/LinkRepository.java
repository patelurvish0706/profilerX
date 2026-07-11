package com.urvish.ProfilerX.repository;

import com.urvish.ProfilerX.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByDeveloperId(Long developerId);
}
