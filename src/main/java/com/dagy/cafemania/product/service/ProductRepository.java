package com.dagy.cafemania.product.service;

import com.dagy.cafemania.product.model.Product;
import com.dagy.cafemania.product.payload.ProductSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByName(String name);
    List<Product> findByCategoryId(String categoryId);

//    @Query(value = """
//            select p from Product p
//            where ( :#{#criteria.name} IS NULL OR p.name LIKE :#{#criteria.name}% )
//            and ( :#{#criteria.price} IS NULL OR p.price LIKE :#{#criteria.price}% )
//            and ( :#{#criteria.email} IS NULL OR emp.email LIKE %:#{#criteria.email}% )
//            """)

    @Query(value = """
            select p from Product p
            where ( :#{#criteria.name} IS NULL OR p.name LIKE :#{#criteria.name}% )
            and ( :#{#criteria.price} IS NULL OR p.price LIKE :#{#criteria.price}% )
            """)
    Page<Product> getAllProductsUsingPagination(
            @Param("criteria") ProductSearchRequest productSearchRequest,
            Pageable pageable);
}
