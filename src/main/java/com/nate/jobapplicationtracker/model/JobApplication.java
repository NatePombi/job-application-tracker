package com.nate.jobapplicationtracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Job title is required")
    private String jobTitle;
    @NotBlank (message = "Company is required")
    private String company;
    private String location;
    private LocalDateTime applicationDate;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;
    private String notes;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
