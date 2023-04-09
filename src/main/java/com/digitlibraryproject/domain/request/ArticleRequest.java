package com.digitlibraryproject.domain.request;

import lombok.Data;

@Data
public class ArticleRequest {
    private String title;
    private String text;
    private int authorId;

    public ArticleRequest() {
    }

    public ArticleRequest(String title, String text, int authorId) {
        this.title = title;
        this.text = text;
        this.authorId = authorId;
    }
}
