package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.dto.JobApplicationDto;
import com.nate.jobapplicationtracker.dto.PostJobAppDto;
import com.nate.jobapplicationtracker.exception.JobApplicationNotFoundException;
import com.nate.jobapplicationtracker.exception.UserNotFoundException;
import com.nate.jobapplicationtracker.mapper.JobApplicationMapper;
import com.nate.jobapplicationtracker.model.JobApplication;
import com.nate.jobapplicationtracker.model.Role;
import com.nate.jobapplicationtracker.model.Status;
import com.nate.jobapplicationtracker.model.User;
import com.nate.jobapplicationtracker.repository.JobApplicationRepository;
import com.nate.jobapplicationtracker.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class JobApplicationService implements IJobApplicationService{

   private JobApplicationRepository repo;
   private UserRepository repoU;

   public JobApplicationService(JobApplicationRepository repo,UserRepository repoU){
       this.repo = repo;
       this.repoU = repoU;
   }

    @Override
    public JobApplicationDto createApplication(PostJobAppDto jobApplication, String username) {
       User user = repoU.findByUsername(username)
               .orElseThrow(()-> new UserNotFoundException(username));


       JobApplication application = JobApplicationMapper.toEntity(jobApplication,user);
       JobApplication saved = repo.save(application);

       return JobApplicationMapper.toDto(saved, user.getId());
    }

    @Override
    public JobApplicationDto getById(Long id) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = repoU.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username));

         JobApplication application = repo.findById(id)
                .orElseThrow(()-> new JobApplicationNotFoundException(id));

         if(user.getRole() != Role.ADMIN){
         ensureOwnership(application, user.getId());
         }


         return JobApplicationMapper.toDto(application,application.getUser().getId());
    }

    @Override
    public List<JobApplicationDto> getAllApplicationByUserId(String username) {
         var user = repoU.findByUsername(username)
                 .orElseThrow(()-> new UserNotFoundException(username));

         return repo.findByUserId(user.getId()).stream()
                 .map(m-> JobApplicationMapper.toDto(m,user.getId()))
                 .toList();
    }

    @Override
    @Transactional
    public JobApplicationDto update(String username ,Long id, JobApplicationDto application) throws AccessDeniedException {
       User user = repoU.findByUsername(username)
               .orElseThrow(()-> new UserNotFoundException(username));

       JobApplication jobApplication = repo.findById(id)
               .orElseThrow(()-> new JobApplicationNotFoundException(id));

       ensureOwnership(jobApplication,user.getId());

       jobApplication.setJobTitle(application.getJobTitle());
       jobApplication.setCompany(application.getCompany());
       jobApplication.setLocation(application.getLocation());

       repo.save(jobApplication);

        return JobApplicationMapper.toDto(jobApplication,jobApplication.getUser().getId());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @Transactional
    public boolean delete(String username, Long id) throws AccessDeniedException {
        JobApplication app = repo.findById(id)
                        .orElseThrow(()-> new JobApplicationNotFoundException(id));


        repo.delete(app);

        return true;
    }

    @Override
    public List<JobApplicationDto> getAll() {
        return repo.findAll().stream()
                .map(m-> JobApplicationMapper.toDto(m,m.getUser().getId()))
                .toList();

    }
@Transactional
    @Override
    public JobApplicationDto updateStatus(String username,Long id, Status status) throws AccessDeniedException {
       User user = repoU.findByUsername(username)
               .orElseThrow(()->new UserNotFoundException(username));

        JobApplication application = repo.findById(id).stream()
                .findFirst()
                .orElseThrow(()-> new JobApplicationNotFoundException(id));

        ensureOwnership(application,user.getId());

        application.setStatus(status);
        return JobApplicationMapper.toDto(application,application.getUser().getId());
    }

    @Transactional
    @Override
    public JobApplicationDto addNoteToApplication(String username, Long id, String notes) throws AccessDeniedException {
       User user = repoU.findByUsername(username)
               .orElseThrow(()-> new UserNotFoundException(username));

       JobApplication application = repo.findById(id).stream()
               .findFirst()
               .orElseThrow(()-> new JobApplicationNotFoundException(id));

       ensureOwnership(application,user.getId());

       application.getNotes().add(notes);

       repo.save(application);

       return JobApplicationMapper.toDto(application,application.getUser().getId());
    }

    private void ensureOwnership(JobApplication app, Long userId) throws AccessDeniedException {

       if(app == null || !app.getUser().getId().equals(userId)){
           throw new AccessDeniedException("Not your application");
       }
    }

}
