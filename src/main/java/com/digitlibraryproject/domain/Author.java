package com.digitlibraryproject.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "author_seq")
    @SequenceGenerator(name = "author_seq",sequenceName = "authors_id_seq",allocationSize = 1)
    private int id;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "biography")
    private String biography;

    @Column(name = "description")
    private String description;

}
