package com.azgroup.bookstore.entity.request;

import lombok.Data;

@Data
public class BookRequest {
    private String name;
    private Long authorId;
}
