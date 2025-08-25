package com.nate.jobapplicationtracker.repository;

import com.nate.jobapplicationtracker.model.JobApplication;
import com.nate.jobapplicationtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
    List<JobApplication> findByUserId(Long id);

}
