package _ieh.example.book_service.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import _ieh.example.book_service.validater.Constraints.YearReleaseConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCreationRequest {
    @Size(min = 3, message = "Name must be should then 3 characters")
    String name;

    @Size(min = 10, message = "Description must be should then 10 characters")
    String description;

    @YearReleaseConstraint()
    int yearRelease;

    @NotNull(message = "Author is required")
    long authorId;

    @NotNull(message = "Category is required")
    List<Long> categoryId;
}
