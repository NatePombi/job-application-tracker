package com.nate.jobapplicationtracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Entity
@Getter @AllArgsConstructor @NoArgsConstructor @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "username is required")
    private String username;
    @Email
    private String email;
    @NotBlank(message = "password is required")
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<JobApplication> jobApplicationList;

}
