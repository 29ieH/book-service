package _ieh.example.book_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import _ieh.example.book_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Boolean existsByUserName(String name);

    public Optional<User> findByUserName(String name);
}
