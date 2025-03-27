package com.ajackus.digitallibrarymanagement.ControllerTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.ajackus.digitallibrarymanagement.models.Book;
import com.ajackus.digitallibrarymanagement.models.Book.AvailabilityStatus;
import com.ajackus.digitallibrarymanagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for the BookController class.
 * Uses Mockito to mock dependencies and Spring MockMvc for testing API endpoints.
 */
class BookControllerTest {

    private MockMvc mockMvc;  // MockMvc allows us to test HTTP requests/responses.

    @Mock
    private BookService bookService;  // Mocking the BookService to simulate service layer behavior.

    @InjectMocks
    private com.ajackus.digitallibrarymanagement.controller.BookController bookController;  // Injecting the mocked service into the controller.

    private Book book1;
    private Book book2;

    /**
     * Runs before each test case to initialize mock objects and set up test data.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initializes the mock objects.
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();  // Setting up MockMvc with the controller.

        // Creating sample books for testing.
        book1 = new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", AvailabilityStatus.AVAILABLE);
        book2 = new Book(2L, "To Kill a Mockingbird", "Harper Lee", "Classic", AvailabilityStatus.CHECKED_OUT);
    }

    /**
     * Test case for adding a new book.
     * Simulates an HTTP POST request and verifies the response.
     */
    @Test
    void testAddBook() throws Exception {
        when(bookService.addBook(any(Book.class))).thenReturn(book1);  // Mocking service method.

        mockMvc.perform(post("/books/addBook")  // Simulating POST request.
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(book1)))  // Sending book details as JSON.
                .andExpect(status().isCreated())  // Expecting HTTP 201 Created.
                .andExpect(jsonPath("$.title").value("The Great Gatsby"));  // Validating the book title in the response.
    }

    /**
     * Test case for fetching all books.
     * Simulates an HTTP GET request and checks if the response contains the correct number of books.
     */
    @Test
    void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(book1, book2);
        when(bookService.getAllBooks()).thenReturn(books);  // Mocking the service method.

        mockMvc.perform(get("/books/getAllBooks"))  // Simulating GET request.
                .andExpect(status().isOk())  // Expecting HTTP 200 OK.
                .andExpect(jsonPath("$.size()").value(2));  // Verifying that 2 books are returned.
    }

    /**
     * Test case for fetching a book by ID.
     * Simulates an HTTP GET request with a query parameter.
     */
    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(book1);  // Mocking service method.

        mockMvc.perform(get("/books/getBookById").param("id", "1"))  // Simulating GET request with ID parameter.
                .andExpect(status().isOk())  // Expecting HTTP 200 OK.
                .andExpect(jsonPath("$.title").value("The Great Gatsby"));  // Verifying book title.
    }

    /**
     * Test case for fetching a book by title.
     * Simulates an HTTP GET request with a title parameter.
     */
    @Test
    void testGetBookByTitle() throws Exception {
        when(bookService.getBookByTitle("The Great Gatsby")).thenReturn(book1);  // Mocking service method.

        mockMvc.perform(get("/books/getBookByTitle").param("title", "The Great Gatsby"))  // Simulating GET request with title.
                .andExpect(status().isOk())  // Expecting HTTP 200 OK.
                .andExpect(jsonPath("$.title").value("The Great Gatsby"));  // Verifying book title.
    }

    /**
     * Test case for updating book details.
     * Simulates an HTTP PATCH request.
     */
    @Test
    void testUpdateBook() throws Exception {
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book1);  // Mocking service method.

        mockMvc.perform(patch("/books/1")  // Simulating PATCH request.
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(book1)))  // Sending updated book details as JSON.
                .andExpect(status().isOk())  // Expecting HTTP 200 OK.
                .andExpect(jsonPath("$.title").value("The Great Gatsby"));  // Verifying book title after update.
    }

    /**
     * Test case for deleting a book.
     * Simulates an HTTP DELETE request.
     */
    @Test
    void testDeleteBook() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(true);  // Mocking service method.

        mockMvc.perform(delete("/books/deleteBook").param("id", "1"))  // Simulating DELETE request with ID parameter.
                .andExpect(status().isOk())  // Expecting HTTP 200 OK.
                .andExpect(content().string("Book deleted successfully"));  // Verifying response message.
    }
}
