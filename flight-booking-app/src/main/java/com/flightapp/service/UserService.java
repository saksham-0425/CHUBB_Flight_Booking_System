package com.flightapp.service;

import com.flightapp.dto.request.RegisterRequest;
import com.flightapp.dto.request.LoginRequest;
import com.flightapp.model.AppUser;

public interface UserService {

    void register(RegisterRequest request);

    AppUser login(LoginRequest request);
}
