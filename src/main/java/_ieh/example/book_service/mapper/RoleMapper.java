package _ieh.example.book_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import _ieh.example.book_service.dto.request.RoleRequest;
import _ieh.example.book_service.dto.response.RoleResponse;
import _ieh.example.book_service.model.Role;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = PermissionMapper.class)
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest rq);

    @Mapping(source = "permissions", target = "permissions")
    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", ignore = true)
    Role toRoleUpdate(RoleRequest update, @MappingTarget Role role);
}
