package _ieh.example.book_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.PermissionRequest;
import _ieh.example.book_service.dto.response.PermissionResponse;
import _ieh.example.book_service.services.Permission.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/permission")
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/add")
    ApiResponse<?> save(@RequestBody PermissionRequest request) {
        return ApiResponse.builder()
                .code(200)
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping("")
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(200)
                .result(permissionService.getAll())
                .build();
    }
}
