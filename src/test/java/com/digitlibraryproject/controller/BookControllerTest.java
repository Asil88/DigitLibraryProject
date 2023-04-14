package com.digitlibraryproject.controller;


import com.digitlibraryproject.domain.Book;
import com.digitlibraryproject.domain.request.BookRequest;
import com.digitlibraryproject.service.BookService;
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


import static com.digitlibraryproject.util.AvailabilityEnum.InStock;
import static com.digitlibraryproject.util.GenreEnum.Drama;
import static org.hamcrest.Matchers.equalTo;
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
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;

    private List<Book> bookList;

    @BeforeEach
    void setUp() {
        book = new Book(1, "kniga", 100.0, Drama, "series", "ne vazno", InStock, 1, "text.txt");
        bookList = new ArrayList<>();
        bookList.add(book);
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testFindAllBooks() throws Exception {
        when(bookService.findAllBooks()).thenReturn(Optional.of(bookList));
        mockMvc.perform(get("/book/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(book.getId())))
                .andExpect(jsonPath("$[0].title", is(book.getTitle())))
                .andExpect(jsonPath("$[0].price", is(book.getPrice())))
                .andExpect(jsonPath("$[0].genre", equalTo(book.getGenre().toString())))
                .andExpect(jsonPath("$[0].series", is(book.getSeries())))
                .andExpect(jsonPath("$[0].annotation", is(book.getAnnotation())))
                .andExpect(jsonPath("$[0].availability", equalTo(book.getAvailability().toString())))
                .andExpect(jsonPath("$[0].authorId", is(book.getAuthorId())))
                .andExpect(jsonPath("$[0].fileName", is(book.getFileName())));
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testGetBookById() throws Exception {
        when(bookService.getBookById(book.getId())).thenReturn(Optional.of(book));
        mockMvc.perform(get("/book/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(book.getId())))
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.price", is(book.getPrice())))
                .andExpect(jsonPath("$.genre", equalTo(book.getGenre().toString())))
                .andExpect(jsonPath("$.series", is(book.getSeries())))
                .andExpect(jsonPath("$.annotation", is(book.getAnnotation())))
                .andExpect(jsonPath("$.availability", equalTo(book.getAvailability().toString())))
                .andExpect(jsonPath("$.authorId", is(book.getAuthorId())))
                .andExpect(jsonPath("$.fileName", is(book.getFileName())));
    }


    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testCreateBook() throws Exception {
        BookRequest newBook = new BookRequest("kniga1", 100.1, Drama, "series", "ne vazno", InStock, 1, "text.txt");
        doNothing().when(bookService).createBook(newBook);

        mockMvc.perform(post("/book/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newBook)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testUpdateBookById() throws Exception {
        doNothing().when(bookService).updateBookById(book);
        mockMvc.perform(put("/book/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "login", password = "password", roles = "ADMIN")
    void testDeleteAuthorById() throws Exception {
        int id = 1;
        when(bookService.deleteBookById(id)).thenReturn(true);

        mockMvc.perform(delete("/book/{id}", id))
                .andExpect(status().isNoContent());
    }
}

