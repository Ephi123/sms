package com.project1.sms.Service.imp;

import com.project1.sms.Service.BookService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.Books;
import com.project1.sms.model.Categories;
import com.project1.sms.repository.BookRepo;
import com.project1.sms.repository.CategoriesRepo;
import com.project1.sms.requestDTO.BookRequest;
import com.project1.sms.requestDTO.CategoryRequest;
import com.project1.sms.responseDto.CategoryResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class BookImpl implements BookService {
    private final BookRepo bookRepo;
    private final CategoriesRepo categoriesRepo;
    //students
    @Override
    public Page<Books> searchBooks(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return bookRepo.searchBooks(keyword, pageable);

    }
//students
    @Override
    public Page<Books> getBooksByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return bookRepo.findByCategoriesId(categoryId, pageable);
    }

    //student
    @Override
    public Page<Books> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return bookRepo.findAll(pageable);
    }

    //admin
    @Override
    public void postBook(BookRequest request) {
        Categories category = categoriesRepo.findById(request.CategoryId()).orElseThrow(() -> new ApiException("Categories not found"));

        Books book =Books.builder().
                title(request.title()).
                description(request.description()).
                url(request.url()).
                imageCover(request.imageCover()).
                categories(category).
                build();
        bookRepo.save(book);

    }
   //admin
    @Override
    public void createCategories(CategoryRequest request) {
          categoriesRepo.save(
                  new Categories(request.category())
          );
    }

    @Override
    public List<CategoryResponse> getCategories() {
        List<Categories> categories = categoriesRepo.findAll(Sort.by("bookCategory").ascending());

        return categories.
                stream().map(CategoryResponse::from).toList();

    }

    @Override
    public Books getBook(Long bookId) {
        return bookRepo.findById(bookId).orElseThrow(() -> new ApiException("book not found"));
    }

    @Override
    public void updateBook(Long bookId, BookRequest request) {
        Books book = bookRepo.findById(bookId).orElseThrow(() -> new ApiException("book is not found"));
        Categories category = categoriesRepo.findById(request.CategoryId()).orElseThrow(() -> new ApiException("category not found"));

        book.setUrl(request.url());
        book.setTitle(request.title());
        book.setUrl(request.url());
        book.setCategories(category);
        book.setImageCover(request.imageCover());
        book.setDescription(request.description());

        bookRepo.save(book);
    }

    @Override
    public void updateCategory(Long categoryId, CategoryRequest request) {
        Categories category = categoriesRepo.findById(categoryId).orElseThrow(() -> new ApiException("category is not found"));
        category.setBookCategory(request.category());
        categoriesRepo.save(category);
    }

    @Override
    public CategoryResponse getCategory(Long categoryId) {
        Categories categories = categoriesRepo.findById(categoryId).orElseThrow(() -> new ApiException("category not found"));

        return CategoryResponse.from(categories);
    }


}
