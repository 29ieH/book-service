package _ieh.example.book_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.CategoryCreationRequest;
import _ieh.example.book_service.model.Category;
import _ieh.example.book_service.services.Category.CategoryService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/category")
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Category>> add(@RequestBody @Valid CategoryCreationRequest request) {
        return ResponseEntity.ok().body(categoryService.add(request));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Category>>> getAll() {
        return ResponseEntity.ok().body(categoryService.categories());
    }
}
