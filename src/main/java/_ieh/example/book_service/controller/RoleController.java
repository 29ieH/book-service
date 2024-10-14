package _ieh.example.book_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.RoleRequest;
import _ieh.example.book_service.dto.response.RoleResponse;
import _ieh.example.book_service.services.Role.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    RoleService roleService;

    @PostMapping("/add")
    ApiResponse<?> save(@RequestBody RoleRequest request) {
        return ApiResponse.builder().code(200).result(roleService.save(request)).build();
    }

    @GetMapping("")
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(200)
                .result(roleService.getAll())
                .build();
    }

    @PutMapping("/update")
    ApiResponse<RoleResponse> update(@RequestParam String name, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .code(200)
                .result(roleService.update(name, request))
                .build();
    }
    ;
}
