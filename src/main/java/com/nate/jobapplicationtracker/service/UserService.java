package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.dto.UserDto;
import com.nate.jobapplicationtracker.exception.UserNotFoundException;
import com.nate.jobapplicationtracker.mapper.UserMapper;
import com.nate.jobapplicationtracker.model.Role;
import com.nate.jobapplicationtracker.model.User;
import com.nate.jobapplicationtracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements IUserService{

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder){
        this.repo =repo;
        this.encoder = encoder;
    }

    @Override
    public UserDto saveUser(UserDto user) {
        User user1 = UserMapper.toEntity(user);
        User user2 = repo.save(user1);
        return UserMapper.toDto(user2);
    }

    @Override
    public UserDto findByUsername(String username) {
        return repo.findByUsername(username)
                .map(UserMapper::toDto)
                .orElseThrow(()-> new UserNotFoundException(username));
    }

    @Override
    public UserDto getUserById(Long id) {
        return repo.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return repo.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public UserDto registerUser(String username, String email, String rwPass) {
        if(repo.existsByUsername(username)){
            throw new RuntimeException("Username Already Exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encoder.encode(rwPass));
        if(email.contains("admin")){
            user.setRole(Role.ADMIN);
        }
        else {
            user.setRole(Role.USER);
        }
        repo.save(user);
        return UserMapper.toDto(user);
    }

}
