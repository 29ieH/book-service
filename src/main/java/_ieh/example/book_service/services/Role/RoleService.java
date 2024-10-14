package _ieh.example.book_service.services.Role;

import java.util.List;

import _ieh.example.book_service.dto.request.RoleRequest;
import _ieh.example.book_service.dto.response.RoleResponse;

public interface RoleService {
    public RoleResponse save(RoleRequest rq);

    public RoleResponse update(String name, RoleRequest rq);

    public List<RoleResponse> getAll();
}
