package com.ajackus.digitallibrarymanagement.service;

import java.util.List;

import com.ajackus.digitallibrarymanagement.models.Book;

public interface BookService {
	
    Book addBook(Book book);
    
    List<Book> getAllBooks();
    
    Book getBookById(Long id);
    
    Book getBookByTitle(String title);
    
    Book updateBook(Long id, Book updatedBook);
    
    void deleteBook(Long id);
}