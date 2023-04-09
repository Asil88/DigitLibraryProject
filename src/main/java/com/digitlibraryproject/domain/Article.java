package com.digitlibraryproject.domain;

import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;
import java.sql.Timestamp;


@Data
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_seq")
    @SequenceGenerator(name = "article_seq", sequenceName = "articles_id_seq", allocationSize = 1)
    private int id;

    @NotBlank(message = "Title cannot be blank")
    @Column(name = "title")
    private String title;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @NotBlank(message = "Text cannot be blank")
    @Column(name = "text")
    private String text;

    @NotNull(message = "Author ID cannot be null")
    @Column(name = "author_id")
    private int authorId;

    public Article() {
    }

    public Article(int id, String title, Timestamp dateCreated, String text, int authorId) {
        this.id = id;
        this.title = title;
        this.dateCreated = dateCreated;
        this.text = text;
        this.authorId = authorId;
    }
}
