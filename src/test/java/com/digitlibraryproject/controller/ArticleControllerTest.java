package com.digitlibraryproject.controller;

import com.digitlibraryproject.domain.Article;
import com.digitlibraryproject.domain.request.ArticleRequest;
import com.digitlibraryproject.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    private Article article;

    private List<Article> articleList;

    @BeforeEach
    void setUp() {
        article = new Article(1, "title", Timestamp.valueOf(LocalDateTime.now()), "Text", 1);
        articleList = new ArrayList<>();
        articleList.add(article);
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testFindAllArticles() throws Exception {
        when(articleService.findAllArticles()).thenReturn(Optional.of(articleList));
        mockMvc.perform(get("/article/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(article.getId())))
                .andExpect(jsonPath("$[0].text", is(article.getText())))
                .andExpect(jsonPath("$[0].authorId", is(article.getAuthorId())));
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testGetArticleById() throws Exception {
        when(articleService.getArticleById(article.getId())).thenReturn(Optional.of(article));
        mockMvc.perform(get("/article/{id}", article.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(article.getId())))
                .andExpect(jsonPath("$.text", is(article.getText())))
                .andExpect(jsonPath("$.authorId", is(article.getAuthorId())));
    }


    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testCreateArticle() throws Exception {
        ArticleRequest newArticle = new ArticleRequest("New title", "Text", 1);
        doNothing().when(articleService).createArticle(newArticle);

        mockMvc.perform(post("/article/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newArticle)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testUpdateArticleById() throws Exception {
        doNothing().when(articleService).updateArticleById(article);

        mockMvc.perform(put("/article/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(article)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testDeleteArticleById() throws Exception {
        int id = 1;
        when(articleService.deleteArticleById(id)).thenReturn(true);

        mockMvc.perform(delete("/article/{id}", id))
                .andExpect(status().isNoContent());
    }
}
