package com.digitlibraryproject.service;

import com.digitlibraryproject.domain.Author;
import com.digitlibraryproject.domain.request.AuthorRequest;
import com.digitlibraryproject.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<List<Author>> findAllAuthors() {
        return Optional.of((ArrayList<Author>) authorRepository.findAll());
    }

    public Optional<Author> getAuthorById(int id) {
        return authorRepository.findById(id);
    }

    public void createAuthor(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());
        author.setSurname(authorRequest.getSurname());
        author.setBiography(authorRequest.getBiography());
        author.setDescription(authorRequest.getDescription());
        authorRepository.save(author);
    }

    public void updateAuthorById(Author author) {
        authorRepository.saveAndFlush(author);
    }

    public boolean deleteAuthorById(Integer id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        try {
            if (optionalAuthor.isPresent()) {
                authorRepository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
