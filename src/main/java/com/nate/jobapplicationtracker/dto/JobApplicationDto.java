package com.nate.jobapplicationtracker.dto;

import com.nate.jobapplicationtracker.model.Status;
import com.nate.jobapplicationtracker.model.User;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class JobApplicationDto {
    private Long id;
    private String jobTitle;
    private String company;
    private String location;
    private LocalDateTime applicationDate;
    private Status status;
    private String notes;
    private User user;
}
