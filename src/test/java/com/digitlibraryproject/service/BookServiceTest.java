package com.digitlibraryproject.service;


import com.digitlibraryproject.domain.Book;
import com.digitlibraryproject.domain.request.BookRequest;
import com.digitlibraryproject.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.digitlibraryproject.util.AvailabilityEnum.InStock;
import static com.digitlibraryproject.util.GenreEnum.Drama;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    private Book book;

    private List<Book> bookList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository);
        book = new Book(1, "kniga", 100.0, Drama, "series", "ne vazno", InStock, 1, "text.txt");
        bookList = new ArrayList<>();
        bookList.add(book);
    }

    @Test
    public void testFindAllBooks() {
        when(bookRepository.findAll()).thenReturn(bookList);
        Optional<List<Book>> optionalBooks = bookService.findAllBooks();

        assertTrue(optionalBooks.isPresent());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookById() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Optional<Book> optionalArticle = bookService.getBookById(book.getId());

        assertTrue(optionalArticle.isPresent());
        verify(bookRepository, times(1)).findById(book.getId());
    }

    @Test
    public void testCreateBook() {
        BookRequest newBook = new BookRequest("kniga1", 100.1, Drama, "series", "ne vazno", InStock, 1, "text.txt");
        bookService.createBook(newBook);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testUpdateBookById() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        book.setTitle("Gore ot Yma");
        bookService.updateBookById(book);

        verify(bookRepository, times(1)).saveAndFlush(book);
    }

    @Test
    public void testDeleteBookById() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        assertTrue(bookService.deleteBookById(book.getId()));
        verify(bookRepository, times(1)).deleteById(book.getId());
    }
}
