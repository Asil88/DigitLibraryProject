package com.digitlibraryproject.domain.request;

import com.digitlibraryproject.util.AvailabilityEnum;
import com.digitlibraryproject.util.GenreEnum;
import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private Double price;
    private GenreEnum genre;
    private String series;
    private String annotation;
    private AvailabilityEnum availability;
    private int authorId;
    private String fileName;

    public BookRequest() {
    }

    public BookRequest(String title, Double price, GenreEnum genre, String series, String annotation, AvailabilityEnum availability, int authorId, String fileName) {
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
