package com.flightapp.service.impl;

import com.flightapp.dto.request.LoginRequest;
import com.flightapp.dto.request.RegisterRequest;
import com.flightapp.model.AppUser;
import com.flightapp.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewUser() {
        RegisterRequest request = new RegisterRequest();
        request.setName("John");
        request.setEmail("john@gmail.com");
        request.setPassword("pass");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(new AppUser());

        userService.register(request);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testLoginValidUser() {

        AppUser user = new AppUser();
        user.setEmail("john@gmail.com");
        user.setPasswordHash("pass");

        LoginRequest request = new LoginRequest();
        request.setEmail("john@gmail.com");
        request.setPassword("pass");

        when(userRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(user));

        AppUser loggedIn = userService.login(request);

        assertEquals("john@gmail.com", loggedIn.getEmail());
    }

}
