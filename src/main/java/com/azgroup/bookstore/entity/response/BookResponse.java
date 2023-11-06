package com.azgroup.bookstore.entity.response;

import lombok.Data;

@Data
public class BookResponse {
    private Long id;
    private String name;
    private Long authorId;
}
