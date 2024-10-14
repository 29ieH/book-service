package _ieh.example.book_service.services.Author;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.AuthorCreationRequest;
import _ieh.example.book_service.dto.response.AuthorCreationInfo;
import _ieh.example.book_service.model.Author;
import _ieh.example.book_service.model.Book;

@Service
public interface AuthorService {
    public ApiResponse<AuthorCreationInfo> addAuthor(AuthorCreationRequest author);

    public Author getAuthorById(long id);

    public List<Author> authors();

    public Author updateAuthor(Author author);

    public List<Book> booksById(long id);

    public Page<Author> getAuthorSortingAndPagination(String sortBy, int page, int pageSize);
}
