package _ieh.example.book_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorCreationRequest {
    @Size(min = 3, message = "Name must be at least 3 characters")
    String name;

    @NotEmpty(message = "Description must be required")
    String description;

    @NotEmpty(message = "Address is required")
    @Size(min = 10, message = "Address should be at least 10 characters")
    String address;
}
