package _ieh.example.book_service.services.Book;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.BookCreationRequest;
import _ieh.example.book_service.model.Book;

public interface BookService {
    public ApiResponse<Book> addBook(BookCreationRequest book);

    public ApiResponse<List<Book>> getBooks();

    public ApiResponse<List<Book>> getBooksByAuthor(long id);

    public ApiResponse<List<Book>> searchByName(String name);

    public ApiResponse<List<Book>> getBooksByCategories(List<Long> categories);

    public ApiResponse<Page<Book>> getBooksByNameAuthor(String Name, Pageable pageable);
}
