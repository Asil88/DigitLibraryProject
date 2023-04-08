package com.digitlibraryproject.controller;


import com.digitlibraryproject.domain.Author;
import com.digitlibraryproject.domain.request.AuthorRequest;
import com.digitlibraryproject.exception.ResourceNotFoundException;
import com.digitlibraryproject.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/author", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Author>> findAllAuthor() {
        List<Author> allAuthors = authorService.findAllAuthors().orElseThrow(() -> new ResourceNotFoundException("AUTHORS_NOT_FOUND"));
        return new ResponseEntity<>(allAuthors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id) {
        Author authorById = authorService.getAuthorById(id).orElseThrow(() -> new ResourceNotFoundException("AUTHOR_NOT_FOUND"));
        return new ResponseEntity<>(authorById, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createAuthor(@RequestBody @Valid AuthorRequest authorRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        authorService.createAuthor(authorRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateAuthorById(@RequestBody @Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        authorService.updateAuthorById(author);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAuthorById(@PathVariable Integer id) {
        boolean isDeleted = authorService.deleteAuthorById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}