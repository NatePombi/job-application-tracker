package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.model.JobApplication;

import java.util.List;

public interface IJobApplicationService {
    JobApplication createApplication(JobApplication jobApplication);
    JobApplication getById(Long id);
    List<JobApplication> getAllApplicationByUserId(Long userId);
    JobApplication update(Long id, JobApplication application);
    boolean delete(Long id);
}
