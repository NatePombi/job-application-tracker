package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User saveUser(User user);
    User findByUsername(String username);
    User getUserById(Long id);
    List<User> getAllUsers();

}
