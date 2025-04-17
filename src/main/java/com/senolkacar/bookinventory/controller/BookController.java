package com.senolkacar.bookinventory.controller;

import com.senolkacar.bookinventory.model.Book;
import com.senolkacar.bookinventory.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET /api/v1/books - Retrieve all books
    @GetMapping
    public ResponseEntity<Iterable<Book>> getAllBooks() {
        Iterable<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // GET /api/v1/books/{id} - Retrieve a single book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok) // If found, return 200 OK with book
                .orElse(ResponseEntity.notFound().build()); // If not found, return 404 Not Found
    }

    // GET /api/v1/books/isbn/{isbn} - Retrieve a single book by ISBN
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // POST /api/v1/books - Create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        if (book.getId() != null) {
            // Prevent clients from specifying an ID during creation
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID must not be provided for new book creation");
        }
        Book savedBook = bookService.saveBook(book);
        // Return 201 Created status with the created book
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    // PUT /api/v1/books/{id} - Update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookService.updateBook(id, bookDetails)
                .map(ResponseEntity::ok) // If update successful, return 200 OK with updated book
                .orElse(ResponseEntity.notFound().build()); // If book not found, return 404 Not Found
    }

    // DELETE /api/v1/books/{id} - Delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            // Return 204 No Content if deletion was successful
            return ResponseEntity.noContent().build();
        } else {
            // Return 404 Not Found if the book didn't exist
            return ResponseEntity.notFound().build();
        }
    }
}