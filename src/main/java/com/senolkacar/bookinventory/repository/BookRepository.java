package com.senolkacar.bookinventory.repository;

import com.senolkacar.bookinventory.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Long> {
    Book findByIsbn(String isbn);
    Iterable<Book> findAllByOrderByTitleAsc();
}
