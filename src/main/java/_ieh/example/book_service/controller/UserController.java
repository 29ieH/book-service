package _ieh.example.book_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.UserCreationRequest;
import _ieh.example.book_service.dto.request.UserUpdateRequest;
import _ieh.example.book_service.dto.response.UserCreationResponse;
import _ieh.example.book_service.services.User.UserService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/user")
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UserCreationResponse>>> getAll() {
        return ResponseEntity.ok().body(userService.users());
    }

    @PostMapping("/add")
    ResponseEntity<ApiResponse<UserCreationResponse>> save(@RequestBody @Valid UserCreationRequest request) {
        return ResponseEntity.ok().body(userService.saveUser(request));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<UserCreationResponse>> update(
            @RequestParam Long id, @RequestBody @Valid UserUpdateRequest request) {
        return ResponseEntity.ok().body(userService.updateUser(id, request));
    }
}
