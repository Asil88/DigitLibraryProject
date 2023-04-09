package com.digitlibraryproject.domain;


import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;


@Data
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @SequenceGenerator(name = "author_seq", sequenceName = "authors_id_seq", allocationSize = 1)
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

    public Author() {
    }

    public Author(int id, String name, String surname, String biography, String description) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.biography = biography;
        this.description = description;
    }
}
