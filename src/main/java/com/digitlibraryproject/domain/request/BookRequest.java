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
}
