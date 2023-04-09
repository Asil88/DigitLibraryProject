package com.digitlibraryproject.controller;

import com.digitlibraryproject.domain.Article;
import com.digitlibraryproject.domain.request.ArticleRequest;
import com.digitlibraryproject.exception.ResourceNotFoundException;
import com.digitlibraryproject.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/article", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Article>> findAllArticle() {
        List<Article> allArticle = articleService.findAllArticles().orElseThrow(() -> new ResourceNotFoundException("ARTICLES_NOT_FOUND"));
        return new ResponseEntity<>(allArticle, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable int id) {
        Article articleById = articleService.getArticleById(id).orElseThrow(() -> new ResourceNotFoundException("ARTICLE_NOT_FOUND"));
        return new ResponseEntity<>(articleById, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createArticle(@RequestBody @Valid ArticleRequest articleRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        articleService.createArticle(articleRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateArticleById(@RequestBody @Valid Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        articleService.updateAuthorById(article);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteArticleById(@PathVariable Integer id) {
        boolean isDeleted = articleService.deleteArticleById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}