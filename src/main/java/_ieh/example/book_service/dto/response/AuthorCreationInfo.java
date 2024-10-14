package _ieh.example.book_service.dto.response;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorCreationInfo {
    String name;
    String description;
    String address;
    LocalDate timeCreate = LocalDate.now();
}
