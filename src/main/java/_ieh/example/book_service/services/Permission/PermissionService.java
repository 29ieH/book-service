package _ieh.example.book_service.services.Permission;

import java.util.List;

import _ieh.example.book_service.dto.request.PermissionRequest;
import _ieh.example.book_service.dto.response.PermissionResponse;

public interface PermissionService {
    public PermissionResponse create(PermissionRequest request);

    public List<PermissionResponse> getAll();
}
