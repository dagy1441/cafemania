package com.dagy.cafemania.product.service;

import com.dagy.cafemania.product.payload.ProductRequest;
import com.dagy.cafemania.product.payload.ProductResponse;
import com.dagy.cafemania.product.payload.ProductSearchRequest;
import com.dagy.cafemania.user.payload.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProductService {

    ProductResponse save(String categoryId, ProductRequest request);

    ProductResponse update(String productId, String categoryId, ProductRequest request);

    ProductResponse findProductById(String categoryId, String productId);

    //ProductResponse findByName(String name);

    List<ProductResponse> findAll();

    List<ProductResponse> getProductsByCategoryId(String categoryId);

    Page<ProductResponse> getAllProductsUsingPagination(ProductSearchRequest productSearchRequest);

    Page<ProductResponse> findWithPaginationAndSorting(int page, int size, String field);

    void delete(String categoryId, String productId);
}
