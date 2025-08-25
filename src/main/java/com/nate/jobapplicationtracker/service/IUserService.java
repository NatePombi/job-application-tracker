package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.dto.UserDto;

import java.util.List;

public interface IUserService {
    UserDto saveUser(UserDto user);
    UserDto findByUsername(String username);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto registerUser(String username, String email, String rwPass);

}
