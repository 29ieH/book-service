package _ieh.example.book_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import _ieh.example.book_service.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByName(String name);
}
