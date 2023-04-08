package com.digitlibraryproject.controller;

import com.digitlibraryproject.domain.Book;
import com.digitlibraryproject.domain.request.BookRequest;
import com.digitlibraryproject.exception.ResourceNotFoundException;
import com.digitlibraryproject.service.BookService;
import com.digitlibraryproject.util.AvailabilityEnum;
import com.digitlibraryproject.util.GenreEnum;
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
@RequestMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }



    @GetMapping("/findAll")
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> allBooks = bookService.findAllBooks().orElseThrow(() -> new ResourceNotFoundException("BOOKS_NOT_FOUND"));
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book bookById = bookService.getBookById(id).orElseThrow(() -> new ResourceNotFoundException("BOOK_NOT_FOUND"));
        return new ResponseEntity<>(bookById, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createBook(@RequestBody @Valid BookRequest bookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        bookService.createBook(bookRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateBookById(@RequestBody @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        bookService.updateBookById(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/price/{id}/{price}")
    public ResponseEntity<HttpStatus> updateBookPrice(@PathVariable int id, @PathVariable Double price) {
        boolean updateOrderPaymentMethod = bookService.updateBookPrice(id, price);
        if (updateOrderPaymentMethod) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/genre/{id}/{genre}")
    public ResponseEntity<HttpStatus> updateBookGenre(@PathVariable int id, @PathVariable GenreEnum genre) {
        boolean updateBookGenre = bookService.updateBookGenre(id, genre);
        if (updateBookGenre) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/availability/{id}/{availability}")
    public ResponseEntity<HttpStatus> updateBookAvailability(@PathVariable int id, @PathVariable AvailabilityEnum availability) {
        boolean updateBookAvailability = bookService.updateBookAvailability(id, availability);
        if (updateBookAvailability) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Integer id) {
        boolean isDeleted = bookService.deleteBookById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}

