package com.digitlibraryproject.domain.request;

import lombok.Data;


@Data
public class AuthorRequest {
    private String name;
    private String surname;
    private String biography;
    private String description;
}
