package _ieh.example.book_service.controller;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.UserCreationRequest;
import _ieh.example.book_service.dto.response.UserCreationResponse;
import _ieh.example.book_service.services.User.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreationRequest request;
    private UserCreationResponse response;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void initData() {
        request = UserCreationRequest.builder()
                .fullName("Tran Thai Hien")
                .userName("nieh29")
                .password("123456abc")
                .address("Dien Duong - Quang Nam")
                .birthDay(Date.valueOf("2003-04-29"))
                .status(false)
                .build();
        response = UserCreationResponse.builder()
                .fullName("Tran Thai Hien")
                .userName("nieh29")
                .address("Dien Duong - Quang Nam")
                .birthDay(Date.valueOf("2003-04-29"))
                .status(false)
                .build();
    }

    @Test
    void createUser_ValidRequest_Success() throws Exception {
        String content = mapper.writeValueAsString(request);
        ApiResponse<UserCreationResponse> rs = ApiResponse.<UserCreationResponse>builder()
                .code(200)
                .result(response)
                .build();
        //    GIVEN (Request - Response)
        Mockito.when(userService.saveUser(ArgumentMatchers.any())).thenReturn(rs);
        //    WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("result.userName").value(response.getUserName()));
        //     THEN
    }
    ;

    @Test
    void createUser_validUserNameLength_fail() throws Exception {
        request.setUserName("aaa");
        String content = mapper.writeValueAsString(request);
        String STRING_EXCEPTION = "Username must be then 4 characters";
        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("payload.userName").value(STRING_EXCEPTION));
    }
}
