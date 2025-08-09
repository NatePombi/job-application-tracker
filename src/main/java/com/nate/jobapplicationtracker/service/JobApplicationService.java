package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.exception.JobApplicationNotFoundException;
import com.nate.jobapplicationtracker.model.JobApplication;
import com.nate.jobapplicationtracker.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobApplicationService implements IJobApplicationService{

   private JobApplicationRepository repo;

   public JobApplicationService(JobApplicationRepository repo){
       this.repo = repo;
   }

    @Override
    public JobApplication createApplication(JobApplication jobApplication) {
        return repo.save(jobApplication);
    }

    @Override
    public JobApplication getById(Long id) {
        return repo.findById(id)
                .orElseThrow(()-> new JobApplicationNotFoundException(id));
    }

    @Override
    public List<JobApplication> getAllApplicationByUserId(Long userId) {
        return repo.findByUserId(userId);
    }

    @Override
    @Transactional
    public JobApplication update(Long id, JobApplication application) {
       JobApplication jobApplication = repo.findById(id)
               .orElseThrow(()-> new JobApplicationNotFoundException(id));

       jobApplication.setJobTitle(application.getJobTitle());
       jobApplication.setCompany(application.getCompany());
       jobApplication.setNotes(application.getNotes());
       jobApplication.setLocation(application.getLocation());
       jobApplication.setStatus(application.getStatus());

       repo.save(jobApplication);

        return jobApplication;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if(!repo.existsById(id)){
            throw new JobApplicationNotFoundException(id);
        }

        repo.deleteById(id);

        return true;
    }
}
