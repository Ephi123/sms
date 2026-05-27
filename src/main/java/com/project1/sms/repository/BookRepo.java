package com.project1.sms.repository;

import com.project1.sms.model.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepo extends JpaRepository<Books,Long> {
    @Query("""
SELECT b
FROM Books b
WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
   OR LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    Page<Books> searchBooks(@Param("keyword") String keyword, Pageable pageable);
    Page<Books> findByCategoriesId(Long categoryId, Pageable pageable);
}
