package com.nate.jobapplicationtracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegisterDto {
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;
}
