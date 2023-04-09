package com.digitlibraryproject.service;


import com.digitlibraryproject.domain.Article;
import com.digitlibraryproject.domain.request.ArticleRequest;
import com.digitlibraryproject.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;

    private ArticleService articleService;

    private Article article;

    private List<Article> articleList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        articleService = new ArticleService(articleRepository);
        article = new Article(1, "title", Timestamp.valueOf(LocalDateTime.now()), "Text", 1);
        articleList = new ArrayList<>();
        articleList.add(article);
    }

    @Test
    public void testFindAllArticles() {
        when(articleRepository.findAll()).thenReturn(articleList);
        Optional<List<Article>> optionalArticles = articleService.findAllArticles();

        assertTrue(optionalArticles.isPresent());
        verify(articleRepository, times(1)).findAll();
    }

    @Test
    public void testGetArticleById() {
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        Optional<Article> optionalArticle = articleService.getArticleById(article.getId());

        assertTrue(optionalArticle.isPresent());
        verify(articleRepository, times(1)).findById(article.getId());
    }

    @Test
    public void testCreateArticle() {
        ArticleRequest newArticle = new ArticleRequest("title", "Text", 1);
        articleService.createArticle(newArticle);
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    public void testUpdateArticleById() {
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        article.setText("Updated Text");
        articleService.updateArticleById(article);

        verify(articleRepository, times(1)).saveAndFlush(article);
    }

    @Test
    public void testDeleteArticleById() {
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        assertTrue(articleService.deleteArticleById(article.getId()));
        verify(articleRepository, times(1)).deleteById(article.getId());
    }
}
