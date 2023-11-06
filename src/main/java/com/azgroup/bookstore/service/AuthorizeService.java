package com.azgroup.bookstore.service;

import com.azgroup.bookstore.entity.request.LoginRequest;
import com.azgroup.bookstore.entity.request.SignupRequest;
import com.azgroup.bookstore.entity.response.JwtResponse;
import com.azgroup.bookstore.entity.response.MessageResponse;
import org.springframework.http.ResponseEntity;

public interface AuthorizeService {
    ResponseEntity<JwtResponse> signIn(LoginRequest loginRequest);

    ResponseEntity<MessageResponse> registerUser(SignupRequest signupRequest);
}
