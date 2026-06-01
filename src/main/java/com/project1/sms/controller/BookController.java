package com.project1.sms.controller;

import com.project1.sms.Service.BookService;
import com.project1.sms.model.Books;
import com.project1.sms.requestDTO.BookRequest;
import com.project1.sms.requestDTO.CategoryRequest;
import com.project1.sms.response.GlobalResponse;
import com.project1.sms.responseDto.CategoryResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public ResponseEntity<GlobalResponse<Page<Books>>> searchBooks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Books> books = bookService.searchBooks(keyword, page, size);
        return ResponseEntity.ok(GlobalResponse.success("Books searched successfully", books));
    }

    @GetMapping("/categories/{categoryId}/books")
    public ResponseEntity<GlobalResponse<Page<Books>>> getBooksByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Books> books = bookService.getBooksByCategory(categoryId, page, size);
        return ResponseEntity.ok(GlobalResponse.success("Books fetched by category successfully", books));
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<Page<Books>>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Books> books = bookService.getAllBooks(page, size);
        return ResponseEntity.ok(GlobalResponse.success("Books fetched successfully", books));
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<?>> postBook(@Valid @RequestBody BookRequest request) {
        bookService.postBook(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "Book created successfully", null));
    }

    @PostMapping("/categories")
    public ResponseEntity<GlobalResponse<?>> createCategories(@Valid @RequestBody CategoryRequest request) {
        bookService.createCategories(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "Category created successfully", null));
    }

    @GetMapping("/categories")
    public ResponseEntity<GlobalResponse<List<CategoryResponse>>> getCategories() {
        List<CategoryResponse> categories = bookService.getCategories();
        return ResponseEntity.ok(GlobalResponse.success("Categories fetched successfully", categories));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<GlobalResponse<Books>> getBook(@PathVariable Long bookId) {
        Books book = bookService.getBook(bookId);
        return ResponseEntity.ok(GlobalResponse.success("Book fetched successfully", book));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<GlobalResponse<?>> updateBook(
            @PathVariable Long bookId,
            @Valid @RequestBody BookRequest request) {
        bookService.updateBook(bookId, request);
        return ResponseEntity.ok(GlobalResponse.success("Book updated successfully", null));
    }

    @PatchMapping("/categories/{categoryId}")
    public ResponseEntity<GlobalResponse<?>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequest request) {
        bookService.updateCategory(categoryId, request);
        return ResponseEntity.ok(GlobalResponse.success("Category updated successfully", null));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<GlobalResponse<CategoryResponse>> getCategory(@PathVariable Long categoryId) {
        CategoryResponse category = bookService.getCategory(categoryId);
        return ResponseEntity.ok(GlobalResponse.success("Category fetched successfully", category));
    }
}
