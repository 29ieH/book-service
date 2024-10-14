package _ieh.example.book_service.services.Permission;

import java.util.List;

import org.springframework.stereotype.Service;

import _ieh.example.book_service.dto.request.PermissionRequest;
import _ieh.example.book_service.dto.response.PermissionResponse;
import _ieh.example.book_service.mapper.PermissionMapper;
import _ieh.example.book_service.model.Permission;
import _ieh.example.book_service.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionServiceImp implements PermissionService {
    PermissionMapper permissionMapper;
    PermissionRepository permissionRepository;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    @Override
    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }
}
