package _ieh.example.book_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import _ieh.example.book_service.dto.request.UserCreationRequest;
import _ieh.example.book_service.dto.request.UserUpdateRequest;
import _ieh.example.book_service.dto.response.UserCreationResponse;
import _ieh.example.book_service.model.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toUserByRequest(UserCreationRequest request);

    UserCreationResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(UserUpdateRequest soruce, @MappingTarget User user);
}
