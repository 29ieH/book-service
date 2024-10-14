package _ieh.example.book_service.services.Author;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import _ieh.example.book_service.api.ApiResponse;
import _ieh.example.book_service.dto.request.AuthorCreationRequest;
import _ieh.example.book_service.dto.response.AuthorCreationInfo;
import _ieh.example.book_service.exception.AppException;
import _ieh.example.book_service.exception.ErrorCode;
import _ieh.example.book_service.mapper.AuthorMapper;
import _ieh.example.book_service.model.Author;
import _ieh.example.book_service.model.Book;
import _ieh.example.book_service.repository.AuthorRepository;
import _ieh.example.book_service.repository.BookRepository;

@Service
public class AuthorServiceImp implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    public ApiResponse<AuthorCreationInfo> addAuthor(AuthorCreationRequest authorDTO) {
        if (authorRepository.existsByName(authorDTO.getName())) {
            throw new AppException(ErrorCode.AUTHORNAME_EXISTED);
        }
        //            Author author = modelMapper.map(authorDTO,Author.class);
        Author author = authorMapper.toAuthor(authorDTO);
        ApiResponse<AuthorCreationInfo> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(modelMapper.map(authorRepository.save(author), AuthorCreationInfo.class));
        return response;
    }

    @Override
    public Author getAuthorById(long id) {

        Optional<Author> author = authorRepository.findById(id);
        if (!author.isPresent()) {
            return null;
        }
        System.out.println(booksById(id));
        return author.get();
    }

    @Override
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @Override
    public Author updateAuthor(Author author) {
        Optional<Author> authorUpdate = authorRepository.findById(author.getId());
        if (authorUpdate.isPresent()) {
            Author authorIsUpdate = authorUpdate.get();
            if (!author.getName().isEmpty()) authorIsUpdate.setName(author.getName());
            if (!author.getDescription().isEmpty()) authorIsUpdate.setDescription(author.getDescription());
            if (!author.getAddress().isEmpty()) authorIsUpdate.setAddress(author.getAddress());
            return authorRepository.save(authorIsUpdate);
        }
        return null;
    }

    @Override
    public List<Book> booksById(long id) {
        return bookRepository.getBookByAuthor_id(id);
    }

    @Override
    public Page<Author> getAuthorSortingAndPagination(String sortProperties, int page, int pageSize) {
        Pageable pageable = null;
        if (!sortProperties.isEmpty()) {
            pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, sortProperties);
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "name");
        }
        return authorRepository.findAll(pageable);
    }
}
