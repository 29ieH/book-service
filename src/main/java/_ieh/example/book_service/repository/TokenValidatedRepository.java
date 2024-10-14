package _ieh.example.book_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import _ieh.example.book_service.model.TokenValidated;

public interface TokenValidatedRepository extends JpaRepository<TokenValidated, String> {
    boolean existsById(String id);
}
