package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.dto.CustomerDetails;
import com.nate.jobapplicationtracker.exception.UserNotFoundException;
import com.nate.jobapplicationtracker.model.User;
import com.nate.jobapplicationtracker.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CustomerDetailService implements UserDetailsService {

    private final UserRepository repo;

    public CustomerDetailService(UserRepository repo){
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = repo.findByUsername(username)
               .orElseThrow(()-> new UserNotFoundException(username));

        return new CustomerDetails(user);
    }
}
