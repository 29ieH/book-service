package _ieh.example.book_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.AuthorCreationRequest;
import _ieh.example.book_service.dto.response.AuthorCreationInfo;
import _ieh.example.book_service.model.Author;
import _ieh.example.book_service.services.Author.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<AuthorCreationInfo>> addAuthor(
            @RequestBody @Valid AuthorCreationRequest authorDTO) {
        return ResponseEntity.ok(authorService.addAuthor(authorDTO));
    }

    @GetMapping("/")
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(authorService.authors());
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getAll(@PathVariable(value = "id", required = false) Long id) {

        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/{page}/{pageSize}/{sortProperties}")
    public ResponseEntity<Page<Author>> getAuthorBySortingAndPagination(
            @PathVariable("page") int page,
            @PathVariable("pageSize") int pageSize,
            @PathVariable("sortProperties") String sortProperties) {
        return ResponseEntity.ok().body(authorService.getAuthorSortingAndPagination(sortProperties, page, pageSize));
    }
}
