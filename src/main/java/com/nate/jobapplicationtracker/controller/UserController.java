package com.nate.jobapplicationtracker.controller;

import com.nate.jobapplicationtracker.dto.UserDto;
import com.nate.jobapplicationtracker.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "User Controller", description = "Endpoints for managing User")
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService service;

    public UserController(IUserService service){
        this.service = service;
    }

    @Operation(summary = "creates a new User")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
        UserDto userDto1 = service.saveUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto1);
    }


    @Operation(summary = "Gets a user by their username")
    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getByUsername(@PathVariable String username){
        return ResponseEntity.ok(service.findByUsername(username));
    }


    @Operation(summary = "Gets a user by their id")
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long user_id){
        return ResponseEntity.ok(service.getUserById(user_id));
    }

    @Operation(summary = "Gets all Users")
    @GetMapping
    public List<UserDto> getAllUsers(){
        return service.getAllUsers();
    }
}
