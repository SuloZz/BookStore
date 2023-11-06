package com.azgroup.bookstore.entity.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class StudentResponse {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String password;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate age;
}
