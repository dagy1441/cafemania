package com.dagy.cafemania.product.controller;


import com.dagy.cafemania.product.payload.ProductRequest;
import com.dagy.cafemania.product.payload.ProductResponse;
import com.dagy.cafemania.product.payload.ProductSearchRequest;
import com.dagy.cafemania.product.service.ProductService;
import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.time.LocalDateTime.now;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<ApiDataResponse> save(String categoryId, ProductRequest request) {


        ApiDataResponse response = ApiDataResponse.builder()
                .time(now())
                .message("Un nouveau produit ajouté")
                .httpStatus(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .data(Map.of("products", productService.save(categoryId, request)))
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiDataResponse> getProductById(String categoryId, String productId) {
        ApiDataResponse response = ApiDataResponse.builder()
                .time(now())
                .message("Récuperer un produit par son identifiant")
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .data(Map.of("products", productService.findProductById(categoryId, productId)))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiDataResponse> getProductsByCategoryId(String categoryId) {
        ApiDataResponse response = ApiDataResponse.builder()
                .time(now())
                .message("Récuperer la liste de produit à partir d'une categorie")
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .data(Map.of("products", productService.getProductsByCategoryId(categoryId)))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiDataResponse> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<ApiDataResponse> findProductWithPaginationAndSorting(int page, int size, String field) {
        return ResponseEntity.ok(
                ApiDataResponse.builder()
                        .time(now())
                        .message("Récupération de tous les utilisateurs")
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .data(Map.of("users", productService.findWithPaginationAndSorting(page, size, field)))
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiDataResponse> getAllProductUsingPagination(ProductSearchRequest searchRequest) {

        ApiDataResponse response = ApiDataResponse.builder()
                .time(now())
                .message("Récuperer la liste des produits par pagination")
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .data(Map.of("products", productService.getAllProductsUsingPagination(searchRequest)))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiDataResponse> update(String categoryId, String productId, ProductRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiDataResponse> delete(String categoryId, String productId) {
        productService.delete(categoryId, productId);

        ApiDataResponse response = ApiDataResponse.builder()
                .time(now())
                .message("Produit supprimé avec succès !")
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
