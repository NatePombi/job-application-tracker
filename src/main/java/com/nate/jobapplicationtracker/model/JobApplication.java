package com.nate.jobapplicationtracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @ElementCollection
    @CollectionTable(
            name = "job_application_notes",
            joinColumns = @JoinColumn(name = "id")
    )
    @Column(name = "notes")
    private List<String> notes = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
