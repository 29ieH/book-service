package _ieh.example.book_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import _ieh.example.book_service.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
