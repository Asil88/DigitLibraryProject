package com.digitlibraryproject.controller;

import com.digitlibraryproject.domain.Author;
import com.digitlibraryproject.domain.request.AuthorRequest;
import com.digitlibraryproject.service.AuthorService;
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
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    private Author author;

    private List<Author> authorList;

    @BeforeEach
    void setUp() {
        author = new Author(1, "Anton", "Lis", "bio", "description");
        authorList = new ArrayList<>();
        authorList.add(author);
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testFindAllAuthors() throws Exception {
        when(authorService.findAllAuthors()).thenReturn(Optional.of(authorList));
        mockMvc.perform(get("/author/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(author.getId())))
                .andExpect(jsonPath("$[0].name", is(author.getName())))
                .andExpect(jsonPath("$[0].surname", is(author.getSurname())))
                .andExpect(jsonPath("$[0].biography", is(author.getBiography())))
                .andExpect(jsonPath("$[0].description", is(author.getDescription())));
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testGetAuthorById() throws Exception {
        when(authorService.getAuthorById(author.getId())).thenReturn(Optional.of(author));
        mockMvc.perform(get("/author/{id}", author.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(author.getId())))
                .andExpect(jsonPath("$.name", is(author.getName())))
                .andExpect(jsonPath("$.surname", is(author.getSurname())))
                .andExpect(jsonPath("$.biography", is(author.getBiography())))
                .andExpect(jsonPath("$.description", is(author.getDescription())));
    }


    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testCreateArticle() throws Exception {
        AuthorRequest newAuthor = new AuthorRequest("Anto", "Li", "bi", "description");
        doNothing().when(authorService).createAuthor(newAuthor);

        mockMvc.perform(post("/author/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newAuthor)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testUpdateAuthorById() throws Exception {
        doNothing().when(authorService).updateAuthorById(author);

        mockMvc.perform(put("/author/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(author)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testDeleteAuthorById() throws Exception {
        int id = 1;
        when(authorService.deleteAuthorById(id)).thenReturn(true);

        mockMvc.perform(delete("/author/{id}", id))
                .andExpect(status().isNoContent());
    }
}
