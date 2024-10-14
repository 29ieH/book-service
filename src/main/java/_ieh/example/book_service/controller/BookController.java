package _ieh.example.book_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.BookCreationRequest;
import _ieh.example.book_service.model.Book;
import _ieh.example.book_service.services.Book.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Book>> addBook(@RequestBody @Valid BookCreationRequest book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Book>>> getAllBook() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/category/{categories}")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksOfCategories(@PathVariable List<Long> categories) {
        return ResponseEntity.ok().body(bookService.getBooksByCategories(categories));
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<ApiResponse<List<Book>>> getBoosByAuthor(@PathVariable(value = "id") long authorId) {
        return ResponseEntity.ok().body(bookService.getBooksByAuthor(authorId));
    }

    @GetMapping("/author")
    public ResponseEntity<ApiResponse<Page<Book>>> getBoosByNameAuthor(@RequestParam String name, Pageable pageable) {
        return ResponseEntity.ok().body(bookService.getBooksByNameAuthor("%" + name + "%", pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Book>>> searchByName(@RequestParam String name) {
        System.out.println("Name: " + name);
        return ResponseEntity.ok().body(bookService.searchByName("%" + name + "%"));
    }
}
