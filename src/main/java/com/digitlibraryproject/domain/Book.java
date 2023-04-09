package com.digitlibraryproject.domain;

import com.digitlibraryproject.util.AvailabilityEnum;
import com.digitlibraryproject.util.GenreEnum;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Enumerated;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;


@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "books_id_seq", allocationSize = 1)
    private int id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
    @DecimalMax(value = "9999.99", message = "Price must be less than or equal to 9999.99")
    @Column(name = "price")
    private Double price;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private GenreEnum genre;

    @Size(max = 255, message = "Series cannot exceed 255 characters")
    @Column(name = "series")
    private String series;

    @Size(max = 2000, message = "Annotation cannot exceed 2000 characters")
    @Column(name = "annotation")
    private String annotation;

    @Column(name = "availability")
    @Enumerated(EnumType.STRING)
    private AvailabilityEnum availability;

    @NotNull(message = "Author ID cannot be null")
    @Column(name = "author_id")
    private int authorId;

    @Column(name = "file_name")
    private String fileName;

    public Book() {
    }

    public Book(int id, String title, Double price, GenreEnum genre, String series, String annotation, AvailabilityEnum availability, int authorId, String fileName) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.genre = genre;
        this.series = series;
        this.annotation = annotation;
        this.availability = availability;
        this.authorId = authorId;
        this.fileName = fileName;
    }
}
