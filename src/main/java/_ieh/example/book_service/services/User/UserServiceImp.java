package _ieh.example.book_service.services.User;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.UserCreationRequest;
import _ieh.example.book_service.dto.request.UserUpdateRequest;
import _ieh.example.book_service.dto.response.UserCreationResponse;
import _ieh.example.book_service.exception.AppException;
import _ieh.example.book_service.exception.ErrorCode;
import _ieh.example.book_service.mapper.UserMapper;
import _ieh.example.book_service.model.Role;
import _ieh.example.book_service.model.User;
import _ieh.example.book_service.repository.RoleRepository;
import _ieh.example.book_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UserServiceImp implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @PreAuthorize("permitAll()")
    @Override
    public ApiResponse<UserCreationResponse> saveUser(UserCreationRequest request) {
        if (!userRepository.existsByUserName(request.getUserName())) {
            ApiResponse<UserCreationResponse> response = new ApiResponse<>();
            response.setCode(200);
            User user = userMapper.toUserByRequest(request);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (request.getRoles() != null) {
                List<Role> roles = roleRepository.findAllById(request.getRoles());
                user.setRoles(new HashSet<>(roles));
            }
            UserCreationResponse userResponse = userMapper.toUserResponse(userRepository.save(user));
            response.setResult(userResponse);
            return response;
        } else {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
    }

    @PreAuthorize("hasAuthority('PROVAL_PERSONAL')")
    @Override
    public ApiResponse<UserCreationResponse> updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_ISNOTAVAILABLE));
        userMapper.updateUser(request, user);
        System.out.println("XYZBABA");
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            List<Role> roles = roleRepository.findAllById(request.getRoles());
            if (roles == null || roles.isEmpty()) {
                throw new AppException(ErrorCode.ROLE_ISNOTAVAILABLE);
            }
            user.setRoles(new HashSet<>(roles));
        } else {
            user.setRoles(new HashSet<>());
        }
        return ApiResponse.<UserCreationResponse>builder()
                .code(200)
                .result(userMapper.toUserResponse(userRepository.save(user)))
                .build();
    }
    //   @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('PROVAL_READALL')")
    @Override
    public ApiResponse<List<UserCreationResponse>> users() {
        var user = SecurityContextHolder.getContext().getAuthentication();
        log.info(user.getName());
        user.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        ApiResponse<List<UserCreationResponse>> response = new ApiResponse<>();
        response.setCode(200);
        List<User> users = userRepository.findAll();
        List<UserCreationResponse> usersResponse =
                users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
        response.setResult(usersResponse);
        return response;
    }
}
