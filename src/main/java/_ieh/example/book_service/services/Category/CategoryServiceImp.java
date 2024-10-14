package _ieh.example.book_service.services.Category;

import java.util.List;

import org.springframework.stereotype.Service;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.CategoryCreationRequest;
import _ieh.example.book_service.mapper.CategoryMapper;
import _ieh.example.book_service.model.Category;
import _ieh.example.book_service.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImp implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    public ApiResponse<Category> add(CategoryCreationRequest request) {
        Category category = categoryMapper.toCategory(request);
        ApiResponse<Category> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(categoryRepository.save(category));
        return response;
    }

    @Override
    public ApiResponse<List<Category>> categories() {
        ApiResponse<List<Category>> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(categoryRepository.findAll());
        return response;
    }
}
