package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.dto.JobApplicationDto;
import com.nate.jobapplicationtracker.dto.PostJobAppDto;
import com.nate.jobapplicationtracker.model.Status;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IJobApplicationService {
    JobApplicationDto createApplication(PostJobAppDto jobApplication, String username);
    JobApplicationDto getById(Long id) throws AccessDeniedException;
    List<JobApplicationDto> getAllApplicationByUserId(String username);
    JobApplicationDto update(String username,Long id, JobApplicationDto application) throws AccessDeniedException;
    boolean delete(String username, Long id) throws AccessDeniedException;
    List<JobApplicationDto> getAll();
    JobApplicationDto updateStatus(String username,Long id , Status status) throws AccessDeniedException;
    JobApplicationDto addNoteToApplication(String username,Long id , String notes) throws AccessDeniedException;
}
