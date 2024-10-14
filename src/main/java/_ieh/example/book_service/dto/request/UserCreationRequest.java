package _ieh.example.book_service.dto.request;

import java.sql.Date;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import _ieh.example.book_service.validater.Constraints.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotEmpty(message = "Full name is required")
    String fullName;

    @NotEmpty(message = "Username is required")
    @Size(min = 4, message = "Username must be then {min} characters")
    String userName;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be then {min} characters ")
    String password;

    String email;

    @DobConstraint(min = 16)
    //    @NotBlank(message = "Birth day is required")
    Date birthDay;

    @NotEmpty(message = "Address is required")
    String address;

    Boolean status = false;
    List<String> roles;
}
