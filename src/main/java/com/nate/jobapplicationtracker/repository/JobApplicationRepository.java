package com.nate.jobapplicationtracker.repository;

import com.nate.jobapplicationtracker.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
}
