package com.project1.sms.Service.imp;

import com.project1.sms.Service.BookService;
import com.project1.sms.model.Books;
import com.project1.sms.repository.BookRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class BookImpl implements BookService {
    private final BookRepo bookRepo;
    @Override
    public Page<Books> searchBooks(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return bookRepo.searchBooks(keyword, pageable);

    }

    @Override
    public Page<Books> getBooksByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return bookRepo.findByCategoriesId(categoryId, pageable);
    }

    @Override
    public Page<Books> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return bookRepo.findAll(pageable);
    }
}
