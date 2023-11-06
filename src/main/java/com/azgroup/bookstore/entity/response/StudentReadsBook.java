package com.azgroup.bookstore.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class StudentReadsBook {
    private Long studentId;
    private List<BookResponse> readBooks;
}
