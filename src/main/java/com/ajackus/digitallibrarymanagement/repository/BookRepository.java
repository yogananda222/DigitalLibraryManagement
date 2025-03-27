package com.ajackus.digitallibrarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajackus.digitallibrarymanagement.models.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
    Optional<Book> findByTitle(String title); // Custom query method to find a book by its title.
}