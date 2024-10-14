package _ieh.example.book_service.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.UserCreationRequest;
import _ieh.example.book_service.dto.request.UserUpdateRequest;
import _ieh.example.book_service.dto.response.UserCreationResponse;
import _ieh.example.book_service.exception.AppException;
import _ieh.example.book_service.model.Role;
import _ieh.example.book_service.model.User;
import _ieh.example.book_service.repository.RoleRepository;
import _ieh.example.book_service.repository.UserRepository;
import _ieh.example.book_service.services.User.UserService;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    UserRepository userRepository;

    private UserCreationRequest request;
    private UserUpdateRequest updateRequest;
    private List<Role> roles;
    private ApiResponse<?> response;
    private User user;
    private List<Role> roleEmpty;

    @BeforeEach
    void initData() {
        request = UserCreationRequest.builder()
                .fullName("Tran Thai Hien")
                .userName("nieh29")
                .password("123456abc")
                .address("Dien Duong - Quang Nam")
                .birthDay(Date.valueOf("2003-04-29"))
                .status(false)
                .roles(Arrays.asList("ADMIN"))
                .build();
        updateRequest = UserUpdateRequest.builder()
                .fullName("Tran Thai Hien")
                .password("123456abc")
                .roles(Arrays.asList("ADMIN"))
                .build();
        response = ApiResponse.<UserCreationResponse>builder()
                .code(200)
                .result(UserCreationResponse.builder()
                        .fullName("Tran Thai Hien")
                        .userName("nieh29")
                        .address("Dien Duong - Quang Nam")
                        .birthDay(Date.valueOf("2003-04-29"))
                        .status(false)
                        .build())
                .build();
        user = User.builder()
                .id(20L)
                .fullName("Tran Thai Hien")
                .userName("nieh29")
                .address("Dien Duong - Quang Nam")
                .birthDay(Date.valueOf("2003-04-29"))
                .status(false)
                .build();
        roles = new ArrayList<>();
        roles.add(Role.builder().name("ADMIN").description("Role is Admin").build());
        roles.add(Role.builder().name("USER").description("Role is User").build());
    }

    @Test
    public void create_ValidRequest_Success() {
        //        GIVEN
        Mockito.when(userRepository.existsByUserName(ArgumentMatchers.anyString()))
                .thenReturn(false);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        Mockito.when(roleRepository.findAllById(ArgumentMatchers.any())).thenReturn(roles);
        //        WHEN
        var responseExectue = userService.saveUser(request);
        //        THEN
        Assertions.assertEquals("nieh29", responseExectue.getResult().getUserName());
    }

    @Test
    public void create_ExitsUserName_Fail() {
        String EXCEPTION_STRING = "User name is available";
        Mockito.when(userRepository.existsByUserName(ArgumentMatchers.anyString()))
                .thenReturn(true);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        var exception = Assertions.assertThrows(AppException.class, () -> userService.saveUser(request));
        Assertions.assertEquals(1006, exception.getErrorCode().getCode());
        Assertions.assertEquals(EXCEPTION_STRING, exception.getErrorCode().getMessage());
    }

    @Test
    @WithMockUser(authorities = {"PROVAL_PERSONAL"})
    public void update_request_success() {
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(roleRepository.findAllById(ArgumentMatchers.any())).thenReturn(roles);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        var response = userService.updateUser(1L, updateRequest);
        Assertions.assertEquals(response.getResult().getUserName(), user.getUserName());
        Assertions.assertEquals(response.getResult().getFullName(), user.getFullName());
    }

    @Test
    @WithMockUser(authorities = {"PROVAL_PERSONAL"})
    public void update_user_fail() {
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(null));
        var exception = Assertions.assertThrows(AppException.class, () -> userService.updateUser(1L, updateRequest));
        Assertions.assertEquals(exception.getErrorCode().getCode(), 1007);
    }

    @Test
    @WithMockUser(authorities = {"PROVAL_PERSONAL"})
    public void update_role_fail() {
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(roleRepository.findAllById(ArgumentMatchers.any())).thenReturn(roleEmpty);
        var exception = Assertions.assertThrows(AppException.class, () -> userService.updateUser(1L, updateRequest));
        Assertions.assertEquals(exception.getErrorCode().getCode(), 1010);
    }

    @Test
    @WithMockUser(authorities = {"PROVAL_PERSONAL"})
    public void update_unrole_succes() {
        updateRequest.setRoles(null);
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        var response = userService.updateUser(1L, updateRequest);
        Assertions.assertEquals(response.getResult().getUserName(), user.getUserName());
        Assertions.assertEquals(response.getResult().getFullName(), user.getFullName());
    }
}
