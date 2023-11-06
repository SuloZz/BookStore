package com.azgroup.bookstore.controller;

import com.azgroup.bookstore.entity.request.LoginRequest;
import com.azgroup.bookstore.entity.request.SignupRequest;
import com.azgroup.bookstore.entity.response.JwtResponse;
import com.azgroup.bookstore.entity.response.MessageResponse;
import com.azgroup.bookstore.service.AuthorizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthorizeService authorizeService;
    @PostMapping("/signing")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authorizeService.signIn(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authorizeService.registerUser(signUpRequest);
    }

}
