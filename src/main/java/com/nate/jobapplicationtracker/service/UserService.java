package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.exception.UserNotFoundException;
import com.nate.jobapplicationtracker.model.User;
import com.nate.jobapplicationtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService implements IUserService{

    private UserRepository repo;

    public UserService(UserRepository repo){
        this.repo =repo;
    }

    @Override
    public User saveUser(User user) {
        return repo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username));
    }

    @Override
    public User getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
