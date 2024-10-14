package _ieh.example.book_service.services.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.BookCreationRequest;
import _ieh.example.book_service.exception.AppException;
import _ieh.example.book_service.exception.ErrorCode;
import _ieh.example.book_service.model.Author;
import _ieh.example.book_service.model.Book;
import _ieh.example.book_service.model.Category;
import _ieh.example.book_service.repository.AuthorRepository;
import _ieh.example.book_service.repository.BookRepository;
import _ieh.example.book_service.repository.CategoryRepository;

@Service
public class BookServiceImp implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ApiResponse<Book> addBook(BookCreationRequest bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        Optional<Author> author = authorRepository.findById(bookDTO.getAuthorId());
        if (author.isPresent()) {
            List<Category> categories = categoryRepository.findAllById(bookDTO.getCategoryId());
            book.setAuthor(author.get());
            if (book.getCategories() == null) {
                book.setCategories(new ArrayList<Category>());
            }
            categories.forEach(category -> {
                book.getCategories().add(category);
            });
            ApiResponse<Book> apiResponse = new ApiResponse<>();
            apiResponse.setCode(200);
            apiResponse.setResult(bookRepository.save(book));
            return apiResponse;
        } else {
            throw new AppException(ErrorCode.AUTHOR_ISNOTAVAILABEL);
        }
    }

    @Override
    public ApiResponse<List<Book>> getBooks() {
        ApiResponse<List<Book>> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(bookRepository.findAll());
        return response;
    }

    @Override
    public ApiResponse<List<Book>> getBooksByAuthor(long id) {
        ApiResponse<List<Book>> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(bookRepository.getBookByAuthor_id(id));
        return response;
    }

    @Override
    public ApiResponse<List<Book>> searchByName(String name) {
        ApiResponse<List<Book>> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(bookRepository.getBookByNameLike(name));
        return response;
    }

    @Override
    public ApiResponse<List<Book>> getBooksByCategories(List<Long> categories) {
        ApiResponse<List<Book>> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(bookRepository.findByCategoriesIn(categories));
        return response;
    }

    @Override
    public ApiResponse<Page<Book>> getBooksByNameAuthor(String name, Pageable pageable) {
        ApiResponse<Page<Book>> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(bookRepository.findByNameAuthor(name, pageable));
        return response;
    }
}
