package com.azgroup.bookstore.service.impl;


import com.azgroup.bookstore.entity.*;
import com.azgroup.bookstore.entity.request.LoginRequest;
import com.azgroup.bookstore.entity.request.SignupRequest;
import com.azgroup.bookstore.entity.response.JwtResponse;
import com.azgroup.bookstore.entity.response.MessageResponse;
import com.azgroup.bookstore.repository.RoleRepository;
import com.azgroup.bookstore.repository.UserRepository;

import com.azgroup.bookstore.security.JwtUtils;
import com.azgroup.bookstore.service.AuthorizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthorizeServiceImpl implements AuthorizeService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    public ResponseEntity<JwtResponse> signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        JwtResponse jwtResponse = new JwtResponse(jwt, "Bearer", userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles);

        return ResponseEntity.ok(jwtResponse);
    }


    @Override
    public ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user;

        if (signUpRequest.getRole() != null && !signUpRequest.getRole().isEmpty()) {
            Set<ERole> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();

            strRoles.forEach(role -> {
                Role userRole = roleRepository.findByName(role)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            });

            if (strRoles.contains(ERole.ROLE_ADMIN)) {

                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: You are not authorized to create an admin user!"));
            }

            if (strRoles.contains(ERole.ROLE_AUTHOR)) {
                Author author = new Author(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
                author.setFullName(signUpRequest.getFullName());
                author.setAge(signUpRequest.getAge());
                author.setRoles(roles);
                author.setIsDeleted(false);
                user = author;
            } else if (strRoles.contains(ERole.ROLE_STUDENT)) {
                Student student = new Student(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
                student.setFullName(signUpRequest.getFullName());
                student.setAge(signUpRequest.getAge());
                student.setRoles(roles);
                student.setIsDeleted(false);
                user = student;
            } else {
                User regularUser = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
                regularUser.setRoles(roles);
                user = regularUser;
            }
        } else {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Default role is not found."));
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);

            User regularUser = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
            regularUser.setRoles(roles);
            user = regularUser;
        }

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }





}