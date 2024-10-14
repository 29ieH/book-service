package _ieh.example.book_service.dto.response;

import java.sql.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationResponse {
    String fullName;
    String userName;
    Date birthDay;
    String address;
    Boolean status;
    Set<RoleResponse> roles;
}
