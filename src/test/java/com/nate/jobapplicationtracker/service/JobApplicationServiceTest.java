package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.dto.JobApplicationDto;
import com.nate.jobapplicationtracker.dto.PostJobAppDto;
import com.nate.jobapplicationtracker.dto.StatusDto;
import com.nate.jobapplicationtracker.exception.JobApplicationNotFoundException;
import com.nate.jobapplicationtracker.exception.UserNotFoundException;
import com.nate.jobapplicationtracker.mapper.JobApplicationMapper;
import com.nate.jobapplicationtracker.model.JobApplication;
import com.nate.jobapplicationtracker.model.Status;
import com.nate.jobapplicationtracker.model.User;
import com.nate.jobapplicationtracker.repository.JobApplicationRepository;
import com.nate.jobapplicationtracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;
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

    @Mock
    UserRepository repoU;

    @Mock
    User mockUser;

    @Mock
    private JobApplication application;



    private JobApplicationService service;






    @BeforeEach
    void startUp(){
        service = new JobApplicationService(repo,repoU);
    }

    @DisplayName("test creating Job Application")
    @Test
    void testCreateJobApplication(){
        PostJobAppDto application = mock(PostJobAppDto.class);
        when(application.getJobTitle()).thenReturn("Tester");
        when(application.getCompany()).thenReturn("Techys");

       User user = new User();
       user.setUsername("tester1");

        when(repoU.findByUsername("tester1")).thenReturn(Optional.of(mockUser));
        when(repo.save(any(JobApplication.class))).thenAnswer(invocation -> invocation.getArgument(0));

        JobApplicationDto application1 = service.createApplication(application,"tester1");

        assertEquals("Tester",application1.getJobTitle(),"Should have the same Job Title");
        assertEquals("Techys",application1.getCompany(),"Should have the same Company name");

        verify(repo, atLeast(1)).save(any(JobApplication.class));
    }

    @DisplayName("Test get all job Applications by User id")
    @Test
    void testGetAllJobApplicationByUserId(){
        JobApplication application2 = mock(JobApplication.class);
        when(mockUser.getId()).thenReturn(2L);
        when(application.getUser()).thenReturn(mockUser);
        when(application2.getUser()).thenReturn(mockUser);
        when(repoU.findByUsername("tester1")).thenReturn(Optional.of(mockUser));

        when(repo.findByUserId(2L)).thenReturn(new ArrayList<>(List.of(application,application2)));

        List<JobApplicationDto> applications = service.getAllApplicationByUserId("tester1");

        assertEquals(2,applications.size(),"should contain 2 job applications");
    }


    @DisplayName("Test get all job Applications by User id: No Job Applications found, should return an empty List")
    @Test
    void testGetAllJobApplicationByUserId_NoApplicationsFound(){
        when(repo.findByUserId(2L)).thenReturn(new ArrayList<>());
        when(repoU.findByUsername("tester1")).thenReturn(Optional.of(mockUser));
        when(mockUser.getId()).thenReturn(2L);
        List<JobApplicationDto> applications = service.getAllApplicationByUserId("tester1");

        assertEquals(0,applications.size());
    }


   @DisplayName("Test Update Job Application")
    @Test
    void testUpdateJobApplication() throws AccessDeniedException {
        List<String> notes = new ArrayList<>();
       User user = new User();
       user.setId(2L);
        PostJobAppDto application = new PostJobAppDto("Tester","Techys","Cape Town", LocalDateTime.now(), Status.APPLIED,notes);
       JobApplicationDto application2 = new JobApplicationDto(3L,"Java Developer","Capaciti","Durban", LocalDateTime.now(), Status.INTERVIEW,notes,user.getId());


       when(repo.findById(2L)).thenReturn(Optional.of(JobApplicationMapper.toEntity(application,user)));
       when(repoU.findByUsername("tester1")).thenReturn(Optional.of(user));

       JobApplicationDto application1 = service.update("tester1",2L,application2);

       assertEquals("Java Developer", application1.getJobTitle());
       assertEquals("Capaciti", application1.getCompany());
       assertEquals("Durban", application1.getLocation());
       assertEquals(Status.APPLIED,application1.getStatus());

       verify(repo,atLeast(1)).save(any(JobApplication.class));

   }


    @DisplayName("Test Update Job Application: Fail application not found , should throw an Exception")
    @Test
    void testUpdateJobApplication_FailApplicationNotFoundShouldThrowException(){
        when(mockUser.getId()).thenReturn(2L);
        when(application.getUser()).thenReturn(mockUser);
        when(repoU.findByUsername("tester1")).thenReturn(Optional.of(mockUser));

        assertThrows(JobApplicationNotFoundException.class,()->{
            service.update("tester1",3L,JobApplicationMapper.toDto(application,application.getId()));
        });
    }


    @DisplayName("Test Update Job Application: Fail user not found , should throw an Exception")
    @Test
    void testUpdateJobApplication_FailUserNotFoundShouldThrowException(){
        when(application.getUser()).thenReturn(mockUser);

        assertThrows(UserNotFoundException.class,()->{
            service.update("tester1",3L,JobApplicationMapper.toDto(application,application.getId()));
        });
    }


    @DisplayName("Test Delete job Application")
    @Test
    void testDeleteJobApplication() throws AccessDeniedException {
        when(repo.findById(3L)).thenReturn(Optional.of(application));
        boolean check = service.delete("tester1",3L);

        assertTrue(check);
    }


    @DisplayName("Test Delete job Application: Fail application not found, should throw an Exception")
    @Test
    void testDeleteJobApplication_FailJobApplicationNotFoundShouldThrowException(){
        assertThrows(JobApplicationNotFoundException.class,()->{
            service.delete("tester1",3L);
        });
    }


    @DisplayName("Test get All Job Applications")
    @Test
    void testGetAllJobApplications(){
        JobApplication application1 = mock(JobApplication.class);
        when(mockUser.getId()).thenReturn(2L);
        when(application1.getUser()).thenReturn(mockUser);
        when(application.getUser()).thenReturn(mockUser);
        when(repo.findAll()).thenReturn(new ArrayList<>(List.of(application1,application)));

        List<JobApplicationDto> applicationDtos = service.getAll();

        assertEquals(2, applicationDtos.size(),"should contain 2 Job applications");
    }

    @DisplayName("Test get All Job Applications: empty, should return an empty List")
    @Test
    void testGetAllJobApplications_NoApplicationsShouldReturnEmptyList(){

        when(repo.findAll()).thenReturn(new ArrayList<>());

        List<JobApplicationDto> applicationDtos = service.getAll();

        assertEquals(0, applicationDtos.size(),"should contain 0 Job applications");
    }

    @DisplayName("Test update status")
    @Test
    void testUpdateStatus() throws AccessDeniedException {
        JobApplication application1 = new JobApplication();
        application1.setUser(mockUser);
        application1.setId(2L);
        when(repo.findById(2L)).thenReturn(Optional.of(application1));
        when(mockUser.getId()).thenReturn(3L);
        when(repoU.findByUsername("tester1")).thenReturn(Optional.of(mockUser));

        JobApplicationDto dto =  service.updateStatus("tester1",2L,Status.INTERVIEW);

        assertEquals(Status.INTERVIEW, dto.getStatus());
    }


    @DisplayName("Test update status: Failed, couldnt find job Application should throw an Exception")
    @Test
    void testUpdateStatus_FailJobApplicationNotFoundShouldThrowException(){
        when(repoU.findByUsername("tester1")).thenReturn(Optional.of(mockUser));
        assertThrows(JobApplicationNotFoundException.class,()->{
            service.updateStatus("tester1",3L,Status.INTERVIEW);
        });
    }

    @DisplayName("Test update status: Failed, couldnt find User should throw an Exception")
    @Test
    void testUpdateStatus_FailUserNotFoundShouldThrowException(){
        assertThrows(UserNotFoundException.class,()->{
            service.updateStatus("tester1",3L,Status.INTERVIEW);
        });
    }


    @DisplayName("Test add Note")
    @Test
    void testAddNoteToApplication() throws AccessDeniedException {
        JobApplication application1 = new JobApplication();
        application1.setId(2L);
        application1.setUser(mockUser);
        when(repo.findById(2L)).thenReturn(Optional.of(application1));
        when(mockUser.getId()).thenReturn(3L);
        when(repoU.findByUsername("tester1")).thenReturn(Optional.of(mockUser));

        JobApplicationDto jobApplicationDto = service.addNoteToApplication("tester1",2L,"Testing");

        assertTrue(jobApplicationDto.getNotes().contains("Testing"));

    }

    @DisplayName("Test add Note: Fail, could not find job application")
    @Test
    void testAddNoteToApplication_FailJobApplicationNotFoundShouldThrowException(){
        when(repoU.findByUsername("tester1")).thenReturn(Optional.of(mockUser));
        assertThrows(JobApplicationNotFoundException.class, ()->{
            service.addNoteToApplication("tester1",2L,"Tester");
        });
    }

    @DisplayName("Test add Note: Fail, could not find User")
    @Test
    void testAddNoteToApplication_FailUserNotFoundShouldThrowException(){
        assertThrows(UserNotFoundException.class, ()->{
            service.addNoteToApplication("tester1",2L,"Tester");
        });
    }
}
