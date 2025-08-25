package com.nate.jobapplicationtracker.controller;

import com.nate.jobapplicationtracker.dto.*;
import com.nate.jobapplicationtracker.mapper.JobApplicationMapper;
import com.nate.jobapplicationtracker.model.JobApplication;
import com.nate.jobapplicationtracker.model.User;
import com.nate.jobapplicationtracker.service.IJobApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.util.TypeCollector;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

import com.nate.jobapplicationtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
@Tag(name = "Job Application Controller", description = "Endpoints for managing job applications")
@RestController
@RequestMapping("/jobapplications")
@SecurityRequirement(name = "bearerAuth")
public class JobApplicationController {

    private final IJobApplicationService service;

    public JobApplicationController(IJobApplicationService service){
        this.service = service;
    }


    @Operation(summary = "Retrieves all applications from a current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "List was return"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public List<JobApplicationDto> getAllUserJobApplications(@AuthenticationPrincipal CustomerDetails currentUser){
        return service.getAllApplicationByUserId(currentUser.getUsername());
    }

    @Operation(summary = "Retrieves an application by its specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "application was returned"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "403",description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")

    })
    @GetMapping("/{app_id}")
    public ResponseEntity<JobApplicationDto> getById(@PathVariable Long app_id) throws AccessDeniedException {
        return ResponseEntity.ok(service.getById(app_id));
    }

    @Operation(summary = "Creates a new job application ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "application was created, returns application"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "403",description = "Forbidden"),

    })
    @PostMapping
    public ResponseEntity<JobApplicationDto> createJobApplication(@RequestBody @Valid PostJobAppDto dto,
                                                                  @AuthenticationPrincipal CustomerDetails currentUser){
        User user = currentUser.getUser();
        JobApplicationDto application = service.createApplication(dto,user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }

    @Operation(summary = "Updates the status of an application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns application with changed status"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "403",description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<JobApplicationDto> statusChange(@PathVariable Long id, @RequestBody @Valid StatusDto statusDto, @AuthenticationPrincipal CustomerDetails currentUser) throws AccessDeniedException {
        return ResponseEntity.ok(service.updateStatus(currentUser.getUsername(), id,statusDto.getStatus()));
    }

    @Operation(summary = "adds a note to the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns application with new added notes"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "403",description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")

    })
    @PostMapping("/{id}/notes")
    public ResponseEntity<JobApplicationDto> addNotes(@PathVariable Long id, @RequestBody @Valid NotesDto note,@AuthenticationPrincipal CustomerDetails currentUser) throws AccessDeniedException {
        return ResponseEntity.ok(service.addNoteToApplication(currentUser.getUsername(),id,note.getNote()));
    }

    @Operation(summary = "Deletes an application by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns true when application is deleted"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "403",description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteJobApplication(@PathVariable Long id, @AuthenticationPrincipal CustomerDetails currentUser) throws AccessDeniedException {
        return ResponseEntity.ok(service.delete(currentUser.getUsername(), id));
    }

    @Operation(summary = "Updates an application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns updated application"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "403",description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")

    })
    @PatchMapping("/{id}")
    public ResponseEntity<JobApplicationDto> updateJobApplication(@PathVariable Long id, @RequestBody @Valid JobApplicationDto dto, @AuthenticationPrincipal CustomerDetails currentUser) throws AccessDeniedException {
        return ResponseEntity.ok(service.update(currentUser.getUsername(),id,dto));
    }
}
