package com.senolkacar.bookinventory.config;

import com.senolkacar.bookinventory.model.Book;
import com.senolkacar.bookinventory.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
// Optional: You might want this runner to execute only during development/testing
// Add 'spring.profiles.active=dev' to your application.properties to activate
// @Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final BookService bookService;

    @Autowired
    public DataInitializer(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Checking if initial book data is needed...");

        // Simple check to avoid inserting duplicates on every restart if H2 is persistent
        if (!bookService.getAllBooks().iterator().hasNext()) {
            log.info("No existing books found. Initializing dummy data...");

            Book book1 = new Book();
            book1.setTitle("The Hitchhiker's Guide to the Galaxy");
            book1.setAuthor("Douglas Adams");
            book1.setIsbn("978-0345391803");
            book1.setNbPages(224);
            bookService.saveBook(book1);
            log.info("Saved: {} by {}", book1.getTitle(), book1.getAuthor());

            Book book2 = new Book();
            book2.setTitle("Pride and Prejudice");
            book2.setAuthor("Jane Austen");
            book2.setIsbn("978-0141439518");
            book2.setNbPages(432);
            bookService.saveBook(book2);
            log.info("Saved: {} by {}", book2.getTitle(), book2.getAuthor());

            Book book3 = new Book();
            book3.setTitle("Dune");
            book3.setAuthor("Frank Herbert");
            book3.setIsbn("978-0441172719");
            book3.setNbPages(412);
            bookService.saveBook(book3);
            log.info("Saved: {} by {}", book3.getTitle(), book3.getAuthor());

            log.info("Dummy data initialization complete.");
        } else {
            log.info("Books already exist in the database. Skipping data initialization.");
        }
    }
}