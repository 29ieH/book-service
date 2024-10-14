package _ieh.example.book_service.services.Role;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import _ieh.example.book_service.dto.request.RoleRequest;
import _ieh.example.book_service.dto.response.RoleResponse;
import _ieh.example.book_service.exception.AppException;
import _ieh.example.book_service.exception.ErrorCode;
import _ieh.example.book_service.mapper.RoleMapper;
import _ieh.example.book_service.model.Permission;
import _ieh.example.book_service.model.Role;
import _ieh.example.book_service.repository.PermissionRepository;
import _ieh.example.book_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse save(RoleRequest rq) {
        Role role = roleMapper.toRole(rq);
        List<Permission> permissions = permissionRepository.findAllById(rq.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse update(String name, RoleRequest rq) {
        log.info("Name: " + name);
        Role role = roleRepository.findById(name).orElseThrow(() -> new AppException(ErrorCode.ROLE_ISNOTAVAILABLE));
        role = roleMapper.toRoleUpdate(rq, role);
        List<Permission> permissions = permissionRepository.findAllById(rq.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        log.info("Role: " + role);
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public List<RoleResponse> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).collect(Collectors.toList());
    }
}
