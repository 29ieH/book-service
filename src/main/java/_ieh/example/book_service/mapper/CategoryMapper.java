package _ieh.example.book_service.mapper;

import org.mapstruct.Mapper;

import _ieh.example.book_service.dto.request.CategoryCreationRequest;
import _ieh.example.book_service.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreationRequest request);
}
