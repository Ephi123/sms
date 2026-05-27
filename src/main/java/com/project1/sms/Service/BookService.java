package com.project1.sms.Service;

import com.project1.sms.model.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<Books> searchBooks(String keyword, int page, int size);
    Page<Books> getBooksByCategory(Long categoryId, int page, int size);
    public Page<Books> getAllBooks(int page, int size);
}
