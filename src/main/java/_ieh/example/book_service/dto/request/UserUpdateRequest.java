package _ieh.example.book_service.dto.request;

import java.sql.Date;
import java.util.List;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    String fullName;

    @Size(min = 8, message = "Password must be then {min} characters ")
    String password;

    String email;
    Date birthDay;

    @Size(min = 10, message = "Address must be than {min} characters")
    String address;

    List<String> roles;
}
