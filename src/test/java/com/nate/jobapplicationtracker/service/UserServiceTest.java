package com.nate.jobapplicationtracker.service;

import com.nate.jobapplicationtracker.exception.UserNotFoundException;
import com.nate.jobapplicationtracker.model.User;
import com.nate.jobapplicationtracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    private UserService service;
    private User mockUser;
    @BeforeEach
    void startUp(){
        service = new UserService(userRepository);
        mockUser = mock(User.class);
    }


    @Test
    void testSaveUser(){
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        User user = service.saveUser(mockUser);


        assertEquals(user,mockUser, "should be the same User");

        verify(userRepository, atLeast(1)).save(any(User.class));

    }

    @DisplayName("Test Find by Username")
    @Test
    void testFindByUsername(){
        when(userRepository.findByUsername("tester")).thenReturn(Optional.of(mockUser));

        User user = service.findByUsername("tester");

        assertEquals(user, mockUser, "should be the same User");
    }

    @DisplayName("Test find by Username: fail, should throw an Exception")
    @Test
    void testFindByUsername_FailShouldThrowException(){
        assertThrows(UserNotFoundException.class,()->{
            service.findByUsername("tester");
        });
    }


    @DisplayName("Test get User by ID")
    @Test
    void testGetUserById(){
        when(userRepository.findById(3L)).thenReturn(Optional.of(mockUser));

        User user = service.getUserById(3L);

        assertEquals(user,mockUser,"should be the same user");
    }

    @DisplayName("Test get User by ID: Fail, should throw an Exception")
    @Test
    void testGetUserById_FailShouldThrowException(){

        assertThrows(UserNotFoundException.class, ()->{
           service.getUserById(3L);
        });
    }


    @DisplayName("Test get All Users")
    @Test
    void testGetAllUsers(){
        User user = mock(User.class);
        when(userRepository.findAll()).thenReturn(new ArrayList<>(List.of(user,mockUser)));

        List<User> users = service.getAllUsers();

        assertEquals(2, users.size(), "Should contain 2 Users");
    }

    @DisplayName("Test get All Users: No User found, should return an empty List")
    @Test
    void testGetAllUsers_NoUsersFound(){

        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<User> users = service.getAllUsers();

        assertEquals(0,users.size(),"Should be an empty list");
    }
}
