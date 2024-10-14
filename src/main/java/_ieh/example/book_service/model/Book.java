package _ieh.example.book_service.model;

import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long Id;

    @Column(length = 50)
    String name;

    @Column(length = 100)
    String description;

    int yearRelease;

    @ManyToOne
    @JoinColumn(name = "author_id")
    Author author;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    List<Category> categories;

    //    @Override
    //    public String toString() {
    //        return "Book{" +
    //                "Id=" + Id +
    //                ", name='" + name + '\'' +
    //                ", description='" + description + '\'' +
    //                ", yearRelease=" + yearRelease +
    //                ", author=" + author +
    //                '}';
    //    }
}
