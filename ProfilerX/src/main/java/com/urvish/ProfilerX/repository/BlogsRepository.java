package com.urvish.ProfilerX.repository;

import com.urvish.ProfilerX.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogsRepository extends JpaRepository<Blogs,Long> {

    Optional<Blogs> findByDeveloperId(Long aLong);

    void deleteByDeveloperId(Long developerId);

}
