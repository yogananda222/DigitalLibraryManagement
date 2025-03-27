package com.ajackus.digitallibrarymanagement.service;

import java.util.List;

import com.ajackus.digitallibrarymanagement.models.Book;

public interface BookService {
	

    Book addBook(Book book); // Adds a new book to the library and returns the saved book object.
    
    List<Book> getAllBooks(); // Retrieves a list of all books available in the library.

    Book getBookById(Long id); // Fetches a specific book based on its unique ID.

    Book getBookByTitle(String title); // Retrieves a book by its title.

    Book updateBook(Long id, Book updatedBook); // Updates an existing book's details using its ID.

    boolean deleteBook(Long id); // Deletes a book from the library using its ID
}