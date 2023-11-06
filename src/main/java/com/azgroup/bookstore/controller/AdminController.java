package com.azgroup.bookstore.controller;

import com.azgroup.bookstore.entity.ERole;
import com.azgroup.bookstore.entity.Role;
import com.azgroup.bookstore.entity.User;
import com.azgroup.bookstore.entity.request.SignupRequest;
import com.azgroup.bookstore.entity.response.AuthorResponseForAdmin;
import com.azgroup.bookstore.entity.response.MessageResponse;
import com.azgroup.bookstore.entity.response.StudentResponse;
import com.azgroup.bookstore.repository.RoleRepository;
import com.azgroup.bookstore.repository.UserRepository;
import com.azgroup.bookstore.service.AuthorService;
import com.azgroup.bookstore.service.BookService;
import com.azgroup.bookstore.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AuthorService authorService;
    private final StudentService studentService;
    private final BookService bookService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    @DeleteMapping("/delete/author/{authorId}")
    ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId){
     authorService.delete(authorId);
     return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/student/{studentId}")
    ResponseEntity<Void> deleteStudent(@PathVariable Long studentId){
        studentService.delete(studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/book/{bookId}")
    public ResponseEntity<Void> delete(@PathVariable Long bookId){
        bookService.delete(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/active/author/accounts")
    public ResponseEntity<List<AuthorResponseForAdmin>> activeAuthorAccounts(){

        return new ResponseEntity<>(authorService.getAllActiveAuthors(),HttpStatus.OK);
    }
    @GetMapping("/deActive/author/accounts")
    public ResponseEntity<List<AuthorResponseForAdmin>> deActiveAuthorAccounts(){
        return new ResponseEntity<>(authorService.getAllDeletedAuthors(),HttpStatus.OK);
    }
    @GetMapping("/active/student/accounts")
    public ResponseEntity<List<StudentResponse>> activeStudentAccounts(){
        return new ResponseEntity<>(studentService.getAllActiveStudent(),HttpStatus.OK);
    }
    @GetMapping("/deActive/student/accounts")
    public ResponseEntity<List<StudentResponse>> deActiveStudentAccounts(){
        return new ResponseEntity<>(studentService.getAllDeletedStudent(),HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createAdminUser(@Valid @RequestBody SignupRequest signUpRequest) {
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

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);

        User adminUser = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        adminUser.setRoles(roles);

        userRepository.save(adminUser);

        return ResponseEntity.ok(new MessageResponse("Admin user created successfully!"));
    }


}

