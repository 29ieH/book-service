package _ieh.example.book_service.mapper;

import org.mapstruct.Mapper;

import _ieh.example.book_service.dto.request.AuthorCreationRequest;
import _ieh.example.book_service.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AuthorCreationRequest request);
}
