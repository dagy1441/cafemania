package com.dagy.cafemania.categories.service;

import com.dagy.cafemania.categories.payload.CategoryRequest;
import com.dagy.cafemania.categories.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse save(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);

    CategoryResponse findById(Long id);

    CategoryResponse findByName(String name);

    List<CategoryResponse> findAll();

    void delete(Long id);
}
