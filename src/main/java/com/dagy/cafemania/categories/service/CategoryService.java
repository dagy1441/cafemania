package com.dagy.cafemania.categories.service;

import com.dagy.cafemania.categories.payload.CategoryRequest;
import com.dagy.cafemania.categories.payload.CategoryResponse;
import com.dagy.cafemania.categories.payload.CategorySearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    CategoryResponse save(CategoryRequest request);

    CategoryResponse update(String id, CategoryRequest request);

    CategoryResponse findById(String id);

    CategoryResponse findByName(String name);

    Page<CategoryResponse> getAllCategoriesUsingPagination(CategorySearchRequest categorySearchRequest);


    List<CategoryResponse> findAll();

    void delete(String id);
}
