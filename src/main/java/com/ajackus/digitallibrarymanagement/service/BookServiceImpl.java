package com.ajackus.digitallibrarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajackus.digitallibrarymanagement.models.Book;
import com.ajackus.digitallibrarymanagement.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepository; // Injects the repository for database operations.

    @Override
    public Book addBook(Book book) {    	
    	// Saves the new book to the database & returns the saved entity
        return bookRepository.save(book); 
    }

	@Override
	public List<Book> getAllBooks() {
		// Fetches all books from the database.
		return bookRepository.findAll(); 
	}

	public Book getBookById(Long bookId) {
		// Retrieves a book by its ID returns null if not found.
	    return bookRepository.findById(bookId).orElse(null);
	}

	@Override
	public Book getBookByTitle(String title) {
		// Retrieves a book by its title; returns null if not found.
		return bookRepository.findByTitle(title).orElse(null);
	}

	@Override
	public Book updateBook(Long id, Book updatedBook) {
		
		  // Fetches the existing book or throws an exception if it doesn't exist.
		
	    Book existingBook = bookRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

	    // Updates book details only if the new values are provided.
	    
	    if (updatedBook.getTitle() != null) {
	        existingBook.setTitle(updatedBook.getTitle());
	    }
	    if (updatedBook.getAuthor() != null) {
	        existingBook.setAuthor(updatedBook.getAuthor());
	    }
	    if(updatedBook.getGenre() != null) {
		    existingBook.setGenre(updatedBook.getGenre());
	    }
	    if(updatedBook.getAvailabilityStatus() != null) {
		    existingBook.setAvailabilityStatus(updatedBook.getAvailabilityStatus());
	    }
	    
	    // Saves and returns the updated book.
	    
	    return bookRepository.save(existingBook);
	}


	@Override
	public boolean deleteBook(Long bookId) {
		
		 // Checks if the book exists before deleting it.
		
	    Optional<Book> bookOptional = bookRepository.findById(bookId);
	    if (bookOptional.isPresent()) {
	        bookRepository.deleteById(bookId);
	        return true;   // Returns true if deletion was successful.
	    }
	    return false; // Returns false if the book was not found.
	}

}
