package com.azgroup.bookstore.entity.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
public class StudentRequest {
    private String fullName;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate age;




}
