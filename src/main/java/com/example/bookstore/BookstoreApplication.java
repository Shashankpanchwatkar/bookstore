package com.example.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Removed: import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// The 'exclude' directive has been removed to allow Spring Boot to auto-configure the database.
@SpringBootApplication
public class BookstoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }
}