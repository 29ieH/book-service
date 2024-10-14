package _ieh.example.book_service.services.User;

import java.util.List;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.UserCreationRequest;
import _ieh.example.book_service.dto.request.UserUpdateRequest;
import _ieh.example.book_service.dto.response.UserCreationResponse;

public interface UserService {
    public ApiResponse<UserCreationResponse> saveUser(UserCreationRequest request);

    public ApiResponse<UserCreationResponse> updateUser(Long userId, UserUpdateRequest request);

    public ApiResponse<List<UserCreationResponse>> users();
}
