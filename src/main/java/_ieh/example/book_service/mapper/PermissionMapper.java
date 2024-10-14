package _ieh.example.book_service.mapper;

import org.mapstruct.Mapper;

import _ieh.example.book_service.dto.request.PermissionRequest;
import _ieh.example.book_service.dto.response.PermissionResponse;
import _ieh.example.book_service.model.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest rq);

    PermissionResponse toPermissionResponse(Permission permission);
}
