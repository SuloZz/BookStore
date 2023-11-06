package com.azgroup.bookstore.entity.response;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ReaderResponse {
    private Long id;
    private String fullName;
    private LocalDate age;
}