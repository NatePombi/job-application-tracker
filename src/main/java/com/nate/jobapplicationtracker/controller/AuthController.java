package com.nate.jobapplicationtracker.controller;

import com.nate.jobapplicationtracker.dto.JwtResponse;
import com.nate.jobapplicationtracker.dto.LoginDto;
import com.nate.jobapplicationtracker.dto.RegisterDto;
import com.nate.jobapplicationtracker.dto.UserDto;
import com.nate.jobapplicationtracker.model.User;
import com.nate.jobapplicationtracker.service.IUserService;
import com.nate.jobapplicationtracker.util.jwtUtil;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import io.jsonwebtoken.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserService service;
    private final AuthenticationManager manager;

    public AuthController(IUserService service, AuthenticationManager manager){
        this.manager = manager;
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterDto registerDto){
        service.registerUser(registerDto.getUsername(), registerDto.getEmail(), registerDto.getPassword());
        return ResponseEntity.ok("User Successfully registered");
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginDto loginDto){

        try{
            Authentication auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

            UserDto user = service.findByUsername(loginDto.getUsername());

            String jwt = jwtUtil.generateToken(loginDto.getUsername(),user.getRole());

            return ResponseEntity.ok(new JwtResponse(jwt));
        }
        catch (AuthenticationException e){
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
