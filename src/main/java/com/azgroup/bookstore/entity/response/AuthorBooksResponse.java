package com.azgroup.bookstore.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class AuthorBooksResponse {
    private Long id;
    private List<BookResponse> books;

}
