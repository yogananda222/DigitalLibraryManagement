package com.ajackus.digitallibrarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajackus.digitallibrarymanagement.models.Book;
import com.ajackus.digitallibrarymanagement.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

	@Override
	public List<Book> getAllBooks() {
		
		return bookRepository.findAll();
	}

	@Override
	public Book getBookById(Long id) {
		
		return bookRepository.getById(id);
	}

	@Override
	public Book getBookByTitle(String title) {
		
		return bookRepository.findByTitle(title).get();
	}

	@Override
	public Book updateBook(Long id, Book updatedBook) {
	    Book existingBook = bookRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

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
	    return bookRepository.save(existingBook);
	}


	@Override
	public void deleteBook(Long id) {
		
		bookRepository.deleteById(id);	
	}

}
