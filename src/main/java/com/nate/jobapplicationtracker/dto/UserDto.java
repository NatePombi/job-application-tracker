package com.nate.jobapplicationtracker.dto;

import com.nate.jobapplicationtracker.model.JobApplication;
import com.nate.jobapplicationtracker.model.Role;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Set<Role> role;
    private List<JobApplication> jobApplicationList;
}
