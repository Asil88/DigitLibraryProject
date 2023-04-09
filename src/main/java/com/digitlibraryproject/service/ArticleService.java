package com.digitlibraryproject.service;

import com.digitlibraryproject.domain.Article;
import com.digitlibraryproject.domain.request.ArticleRequest;
import com.digitlibraryproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Optional<List<Article>> findAllArticles() {
        return Optional.of((ArrayList<Article>) articleRepository.findAll());
    }

    public Optional<Article> getArticleById(int id) {
        return articleRepository.findById(id);
    }

    public void createArticle(ArticleRequest articleRequest) {
        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
        article.setText(articleRequest.getText());
        article.setAuthorId(articleRequest.getAuthorId());
        articleRepository.save(article);
    }

    public void updateAuthorById(Article article) {
        articleRepository.saveAndFlush(article);
    }

    public boolean deleteArticleById(Integer id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        try {
            if (optionalArticle.isPresent()) {
                articleRepository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
