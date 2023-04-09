package com.digitlibraryproject.service;


import com.digitlibraryproject.domain.Author;
import com.digitlibraryproject.domain.request.AuthorRequest;
import com.digitlibraryproject.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    private AuthorService authorService;

    private Author author;

    private List<Author> authorList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authorService = new AuthorService(authorRepository);
        author = new Author(1, "Anton", "Lis", "bio", "description");
        authorList = new ArrayList<>();
        authorList.add(author);
    }

    @Test
    public void testFindAllAuthors() {
        when(authorRepository.findAll()).thenReturn(authorList);
        Optional<List<Author>> optionalAuthors = authorService.findAllAuthors();

        assertTrue(optionalAuthors.isPresent());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void testGetAuthorById() {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        Optional<Author> optionalArticle = authorService.getAuthorById(author.getId());

        assertTrue(optionalArticle.isPresent());
        verify(authorRepository, times(1)).findById(author.getId());
    }

    @Test
    public void testCreateAuthor() {
        AuthorRequest newAuthor = new AuthorRequest("Anton", "Li", "ne vazno", "de");
        authorService.createAuthor(newAuthor);
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    public void testUpdateAuthorById() {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        author.setName("Aleksey");
        authorService.updateAuthorById(author);

        verify(authorRepository, times(1)).saveAndFlush(author);
    }

    @Test
    public void testDeleteAuthorById() {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        assertTrue(authorService.deleteAuthorById(author.getId()));
        verify(authorRepository, times(1)).deleteById(author.getId());
    }
}
