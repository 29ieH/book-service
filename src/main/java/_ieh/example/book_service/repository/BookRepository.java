package _ieh.example.book_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import _ieh.example.book_service.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> getBookByAuthor_id(long id);

    public List<Book> getBookByNameLike(String name);

    @Query(value = "select b from Book b join b.categories c where c.id in :categoriesId")
    public List<Book> findByCategoriesIn(@Param("categoriesId") List<Long> categoriesId);

    @Query(value = "select b from Book b join b.author a where a.name like :name")
    public Page<Book> findByNameAuthor(@Param("name") String name, Pageable pageable);
}
