package com.nate.jobapplicationtracker.repository;

import com.nate.jobapplicationtracker.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
    List<JobApplication> findByUserId(Long id);
}
