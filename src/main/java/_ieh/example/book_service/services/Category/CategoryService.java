package _ieh.example.book_service.services.Category;

import java.util.List;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.CategoryCreationRequest;
import _ieh.example.book_service.model.Category;

public interface CategoryService {
    public ApiResponse<Category> add(CategoryCreationRequest request);

    public ApiResponse<List<Category>> categories();
}
