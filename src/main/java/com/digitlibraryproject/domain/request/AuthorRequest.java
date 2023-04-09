package com.digitlibraryproject.domain.request;

import lombok.Data;


@Data
public class AuthorRequest {
    private String name;
    private String surname;
    private String biography;
    private String description;

    public AuthorRequest() {
    }

    public AuthorRequest(String name, String surname, String biography, String description) {
        this.name = name;
        this.surname = surname;
        this.biography = biography;
        this.description = description;
    }
}
