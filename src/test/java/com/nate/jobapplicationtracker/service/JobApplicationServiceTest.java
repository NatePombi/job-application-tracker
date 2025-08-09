package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.exception.JobApplicationNotFoundException;
import com.nate.jobapplicationtracker.model.JobApplication;
import com.nate.jobapplicationtracker.model.Status;
import com.nate.jobapplicationtracker.model.User;
import com.nate.jobapplicationtracker.repository.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobApplicationServiceTest {

    @Mock
    JobApplicationRepository repo;

    private JobApplicationService service;

    private JobApplication mockApplication;


    @BeforeEach
    void startUp(){
        service = new JobApplicationService(repo);
        mockApplication = mock(JobApplication.class);
    }

    @DisplayName("test creating Job Application")
    @Test
    void testCreateJobApplication(){
        when(repo.save(mockApplication)).thenReturn(mockApplication);

        JobApplication application = service.createApplication(mockApplication);

        assertEquals(application,mockApplication, "should be the same job application");

        verify(repo, atLeast(1)).save(any(JobApplication.class));
    }


    @DisplayName("Test get Job Application by ID")
    @Test
    void testGetJobApplicationById(){
        when(repo.findById(3L)).thenReturn(Optional.of(mockApplication));

        JobApplication application = service.getById(3L);

        assertEquals(application, mockApplication,"Should be the same application");
    }

    @DisplayName("Test get Job Application by ID: Fail, should throw an Exception")
    @Test
    void testGetJobApplicationById_FailShouldThrowException(){

        assertThrows(JobApplicationNotFoundException.class,()->{
            service.getById(3L);
        });
    }

    @DisplayName("Test get all job Applications by User id")
    @Test
    void testGetAllJobApplicationByUserId(){
        JobApplication application = mock(JobApplication.class);

        when(repo.findByUserId(3L)).thenReturn(new ArrayList<>(List.of(mockApplication,application)));

        List<JobApplication> applications = service.getAllApplicationByUserId(3L);

        assertEquals(2,applications.size(),"should contain 2 job applications");
    }


    @DisplayName("Test get all job Applications by User id: No Job Applications found, should return an empty List")
    @Test
    void testGetAllJobApplicationByUserId_NoApplicationsFound(){
        when(repo.findByUserId(2L)).thenReturn(new ArrayList<>());

        List<JobApplication> applications = service.getAllApplicationByUserId(2L);

        assertEquals(0,applications.size());
    }


   @DisplayName("Test Update Job Application")
    @Test
    void testUpdateJobApplication(){
        User user = mock(User.class);
        JobApplication application = new JobApplication(2L,"Tester","Techys","Cape Town", LocalDateTime.now(), Status.APPLIED,"testing",user);
       JobApplication application2 = new JobApplication(3L,"Java Developer","Capaciti","Durban", LocalDateTime.now(), Status.INTERVIEW,"developing",user);

       when(repo.findById(2L)).thenReturn(Optional.of(application));

       JobApplication application1 = service.update(2L,application2);

       assertEquals("Java Developer", application1.getJobTitle());
       assertEquals("Capaciti", application1.getCompany());
       assertEquals("Durban", application1.getLocation());
       assertEquals(Status.INTERVIEW,application1.getStatus());
       assertEquals("developing", application1.getNotes());

       verify(repo,atLeast(1)).save(any(JobApplication.class));

   }


    @DisplayName("Test Update Job Application: Fail, should throw an Exception")
    @Test
    void testUpdateJobApplication_FailShouldThrowException(){

        assertThrows(JobApplicationNotFoundException.class,()->{
            service.update(3L,mockApplication);
        });
    }


    @DisplayName("Test Delete job Application")
    @Test
    void testDeleteJobApplication(){
        when(repo.existsById(3L)).thenReturn(true);

        boolean check = service.delete(3L);

        assertTrue(check);
    }


    @DisplayName("Test Delete job Application: Fail, should throw an Exception")
    @Test
    void testDeleteJobApplication_FailShouldThrowException(){

        assertThrows(JobApplicationNotFoundException.class,()->{
            service.delete(3L);
        });
    }

}
