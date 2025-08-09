package com.nate.jobapplicationtracker.exception;

public class JobApplicationNotFoundException extends RuntimeException {
    public JobApplicationNotFoundException(Long id) {
        super("Job Application with id " + id + " was not found");
    }
}
