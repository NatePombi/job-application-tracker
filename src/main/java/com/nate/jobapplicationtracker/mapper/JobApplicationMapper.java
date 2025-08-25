package com.nate.jobapplicationtracker.mapper;

import com.nate.jobapplicationtracker.dto.JobApplicationDto;
import com.nate.jobapplicationtracker.dto.PostJobAppDto;
import com.nate.jobapplicationtracker.model.JobApplication;
import com.nate.jobapplicationtracker.model.User;

import java.time.LocalDateTime;

public class JobApplicationMapper {



    public static JobApplication toEntity(PostJobAppDto dto, User user){
        if(dto == null){
            return null;
        }

        return new JobApplication(
                user.getId(),
                dto.getJobTitle(),
                dto.getCompany(),
                dto.getLocation(),
                LocalDateTime.now(),
                dto.getStatus(),
                dto.getNotes(),
                user
        );
    }

    public static JobApplicationDto toDto(JobApplication application, Long userId){
        if(application == null){
            return null;
        }


        return new JobApplicationDto(
                application.getId(),
                application.getJobTitle(),
                application.getCompany(),
                application.getLocation(),
                application.getApplicationDate(),
                application.getStatus(),
                application.getNotes(),
                application.getUser() != null ? application.getUser().getId() : null
        );
    }
}
