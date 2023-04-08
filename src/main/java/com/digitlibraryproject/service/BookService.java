package com.digitlibraryproject.service;

import com.digitlibraryproject.domain.Book;
import com.digitlibraryproject.domain.request.BookRequest;
import com.digitlibraryproject.repository.BookRepository;
import com.digitlibraryproject.util.AvailabilityEnum;
import com.digitlibraryproject.util.GenreEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Optional<List<Book>> findAllBooks() {
        return Optional.of((ArrayList<Book>) bookRepository.findAll());
    }

    public Optional<Book> getBookById(int id) {
        return bookRepository.findById(id);
    }

    public void createBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setPrice(bookRequest.getPrice());
        book.setGenre(bookRequest.getGenre());
        book.setSeries(bookRequest.getSeries());
        book.setAnnotation(bookRequest.getAnnotation());
        book.setAvailability(bookRequest.getAvailability());
        book.setAuthorId(bookRequest.getAuthorId());
        book.setFileName(bookRequest.getFileName());
        bookRepository.save(book);
    }

    public void updateBookById(Book book) {
        bookRepository.saveAndFlush(book);
    }

    public boolean updateBookPrice(int id, Double price) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setPrice(price);
            bookRepository.saveAndFlush(book);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateBookGenre(int id, GenreEnum genre) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setGenre(genre);
            bookRepository.saveAndFlush(book);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateBookAvailability(int id, AvailabilityEnum availability) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setAvailability(availability);
            bookRepository.saveAndFlush(book);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteBookById(Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        try {
            if (optionalBook.isPresent()) {
                bookRepository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
