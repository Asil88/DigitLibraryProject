package com.digitlibraryproject.domain.request;

import lombok.Data;

@Data
public class ArticleRequest {
    private String title;
    private String text;
    private int authorId;
}
