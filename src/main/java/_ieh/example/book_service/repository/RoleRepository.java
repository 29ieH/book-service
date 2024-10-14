package _ieh.example.book_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import _ieh.example.book_service.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {}
