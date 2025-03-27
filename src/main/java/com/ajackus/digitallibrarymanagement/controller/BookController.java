package com.ajackus.digitallibrarymanagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ajackus.digitallibrarymanagement.models.Book;
import com.ajackus.digitallibrarymanagement.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService bookService; // Injecting BookService to handle business logic.


 // Endpoint to add a new book
    @PostMapping("/addBook")     
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book createdBook = bookService.addBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED); // Returns 201 Created status.
    }

    // Endpoint to retrieve all books
    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK); // Returns 200 OK status with book list.
    }

    // Endpoint to retrieve a book by its title
    @GetMapping("/getBookByTitle")
    public ResponseEntity<Book> getBookByTitle(@RequestParam String title) {
        Book book = bookService.getBookByTitle(title);
        return book != null 
            ? new ResponseEntity<>(book, HttpStatus.OK) // Book found, return 200 OK.
            : new ResponseEntity<>(HttpStatus.NOT_FOUND); // Book not found, return 404 Not Found.
    }

    // Endpoint to retrieve a book by its ID
    @GetMapping("/getBookById")
    public ResponseEntity<Book> getBookById(@RequestParam Long id) {
        Book book = bookService.getBookById(id);
        return book != null 
            ? new ResponseEntity<>(book, HttpStatus.OK) // Book found, return 200 OK.
            : new ResponseEntity<>(HttpStatus.NOT_FOUND); // Book not found, return 404 Not Found.
    }

    // Endpoint to update a book's details (only specified fields will be updated)
    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book updated = bookService.updateBook(id, updatedBook);
        return updated != null 
            ? new ResponseEntity<>(updated, HttpStatus.OK)  // Update successful, return 200 OK.
            : new ResponseEntity<>(HttpStatus.NOT_FOUND); // Book not found, return 404 Not Found.
    }

    // Endpoint to delete a book by its ID
    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestParam Long id) {
        boolean isDeleted = bookService.deleteBook(id);
        return isDeleted 
            ? new ResponseEntity<>("Book deleted successfully", HttpStatus.OK)  // Deletion successful.
            : new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);// Book not found.
    }
}
