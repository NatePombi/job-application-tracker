package com.nate.jobapplicationtracker.dto;

import com.nate.jobapplicationtracker.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class PostJobAppDto {
    private String jobTitle;
    private String company;
    private String location;
    private LocalDateTime applicationDate;
    private Status status;
    private List<String> notes;
}
