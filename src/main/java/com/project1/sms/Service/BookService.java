package com.project1.sms.Service;

import com.project1.sms.model.Books;
import com.project1.sms.requestDTO.BookRequest;
import com.project1.sms.requestDTO.CategoryRequest;
import com.project1.sms.responseDto.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    Page<Books> searchBooks(String keyword, int page, int size);
    Page<Books> getBooksByCategory(Long categoryId, int page, int size);
    public Page<Books> getAllBooks(int page, int size);
    void postBook(BookRequest request);
    void createCategories(CategoryRequest request);

    List<CategoryResponse> getCategories();

    Books getBook(Long bookId);
    void updateBook(Long bookId,BookRequest request);
    void updateCategory(Long categoryId,CategoryRequest request);
    CategoryResponse getCategory(Long categoryId);
}
