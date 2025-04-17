package com.senolkacar.bookinventory.service;

import com.senolkacar.bookinventory.model.Book;
import com.senolkacar.bookinventory.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> getAllBooks() {
        // Using the custom query method to order by title
        return bookRepository.findAllByOrderByTitleAsc();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

     public Optional<Book> getBookByIsbn(String isbn) {
        return Optional.ofNullable(bookRepository.findByIsbn(isbn));
    }

    @Transactional
    public Book saveBook(Book book) {
        // Additional business logic can be added here before saving
        return bookRepository.save(book);
    }

    @Transactional
    public Optional<Book> updateBook(Long id, Book bookDetails) {
       return bookRepository.findById(id)
               .map(existingBook -> {
                   existingBook.setTitle(bookDetails.getTitle());
                   existingBook.setAuthor(bookDetails.getAuthor());
                   existingBook.setIsbn(bookDetails.getIsbn());
                   existingBook.setNbPages(bookDetails.getNbPages());
                   return bookRepository.save(existingBook);
               });
    }


    @Transactional
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}