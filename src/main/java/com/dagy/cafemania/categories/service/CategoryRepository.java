package com.dagy.cafemania.categories.service;

import com.dagy.cafemania.categories.Category;
import com.dagy.cafemania.categories.payload.CategorySearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByName(String name);

    @Query("""
        SELECT cat FROM Category cat
        WHERE (:#{#criteria.name} IS NULL OR cat.name LIKE %:#{#criteria.name}%)
        AND (:#{#criteria.description} IS NULL OR cat.description LIKE %:#{#criteria.description}%)
        """)
    Page<Category> getAllCategoriesUsingPagination(
            @Param("criteria") CategorySearchRequest categorySearchRequest,
            Pageable pageable);

}
