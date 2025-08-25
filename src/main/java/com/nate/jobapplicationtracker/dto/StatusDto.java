package com.nate.jobapplicationtracker.dto;

import com.nate.jobapplicationtracker.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class StatusDto {
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private Status status;

}
