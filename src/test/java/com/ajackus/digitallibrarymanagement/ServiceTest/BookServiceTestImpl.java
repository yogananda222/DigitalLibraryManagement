package com.ajackus.digitallibrarymanagement.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ajackus.digitallibrarymanagement.models.Book;
import com.ajackus.digitallibrarymanagement.models.Book.AvailabilityStatus;
import com.ajackus.digitallibrarymanagement.repository.BookRepository;
import com.ajackus.digitallibrarymanagement.service.BookServiceImpl;

class BookServiceTestImpl {

    @Mock
    private BookRepository bookRepository; // Mocking the repository to simulate database interactions

    @InjectMocks
    private BookServiceImpl bookService; // Injecting mocks into the service class

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() { 

        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
        
        // Creating sample book objects for testing

        book1 = new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", AvailabilityStatus.AVAILABLE);
        book2 = new Book(2L, "To Kill a Mockingbird", "Harper Lee", "Classic", AvailabilityStatus.CHECKED_OUT);
    }

    @Test
    void testAddBook() {
    	
        // Mocking the save method to return the first book when called
        when(bookRepository.save(any(Book.class))).thenReturn(book1);
        
        // Calling the service method
        Book savedBook = bookService.addBook(book1);
        
        // Validating the response
        assertNotNull(savedBook);
        assertEquals("The Great Gatsby", savedBook.getTitle());
        assertEquals("F. Scott Fitzgerald", savedBook.getAuthor());
        
        // Verifying that the repository's save method was called exactly once
        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    void testGetAllBooks() {
    	
        // Mocking the repository to return a list of books
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Calling the service method
        List<Book> books = bookService.getAllBooks();

     // Validating the response
        assertNotNull(books);
        assertEquals(2, books.size());
        
        // Ensuring findAll was called exactly once
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById_Found() {
    	 // Simulating a book being found in the repository
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        // Fetching the book by ID
        Book foundBook = bookService.getBookById(1L);

        // Checking if the book is correctly retrieved
        assertNotNull(foundBook);
        assertEquals(1L, foundBook.getBookId());
        
     // Verifying the repository call
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
    	
    	 // Simulating no book found for the given ID
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());

     // Fetching the book, which should return null
        Book foundBook = bookService.getBookById(3L);
        
     // Since book does not exist, it should be null
        assertNull(foundBook);
        
        // Ensuring the repository was queried once
        verify(bookRepository, times(1)).findById(3L);
    }
    
    @Test
    void testGetBookByTitle_Found() {
    	
    	// Mocking repository to return a book by title
        when(bookRepository.findByTitle("The Great Gatsby")).thenReturn(Optional.of(book1));

        // Fetching the book by title
        Book foundBook = bookService.getBookByTitle("The Great Gatsby");

        // Validating the response
        assertNotNull(foundBook);
        assertEquals("The Great Gatsby", foundBook.getTitle());
        
        // Verifying repository interaction
        verify(bookRepository, times(1)).findByTitle("The Great Gatsby");
    }

    @Test
    void testGetBookByTitle_NotFound() {
    	
    	// Simulating title not found scenario
        when(bookRepository.findByTitle("Unknown Book")).thenReturn(Optional.empty());

        // Fetching the book, which should return null
        Book foundBook = bookService.getBookByTitle("Unknown Book");
        
        assertNull(foundBook);  // Since no book exists, it should be null
		
		// Verifying repository interaction
        verify(bookRepository, times(1)).findByTitle("Unknown Book");
    }


    @Test
    void testUpdateBook_Found() {
    	// Simulating finding an existing book in the repository
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

     // Updating the book's title
        book1.setTitle("Updated Title");
        Book updatedBook = bookService.updateBook(1L, book1);

        // Validating the updated book details
        assertNotNull(updatedBook);
        assertEquals("Updated Title", updatedBook.getTitle());
        
        // Ensuring the save method was called
        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    void testUpdateBook_NotFound() {
    	
    	// Simulating a scenario where the book does not exist
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());

     // Expecting an exception when trying to update a non-existent book
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookService.updateBook(3L, book1);
        });

        assertEquals("Book not found with id: 3", exception.getMessage());
        
        // Ensuring that save was never called since the book didn't exist
        verify(bookRepository, times(0)).save(any(Book.class));
    }

    
    

    @Test
    void testDeleteBook_Found() {
    	
    	 // Simulating finding a book in the repository
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        
        // Mocking deleteById to do nothing
        doNothing().when(bookRepository).deleteById(1L);

        // Calling deleteBook method
        boolean isDeleted = bookService.deleteBook(1L);

        // Ensuring the book was successfully deleted
        assertTrue(isDeleted);  
        
        // Verifying deleteById was called once
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBook_NotFound() {
    	
    	 // Simulating no book found for the given ID
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());

        // Calling deleteBook, which should return false since the book doesn't exist
        boolean isDeleted = bookService.deleteBook(3L);

        // Ensuring the delete operation was unsuccessful
        assertFalse(isDeleted);  
        
        // Ensuring deleteById was never called since book was not found
        verify(bookRepository, times(0)).deleteById(anyLong());
    }
}