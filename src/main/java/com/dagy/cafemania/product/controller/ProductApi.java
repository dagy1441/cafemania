package com.dagy.cafemania.product.controller;


import com.dagy.cafemania.categories.payload.CategoryRequest;
import com.dagy.cafemania.product.model.Product;
import com.dagy.cafemania.product.payload.ProductRequest;
import com.dagy.cafemania.product.payload.ProductSearchRequest;
import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import com.dagy.cafemania.user.User;
import com.dagy.cafemania.user.payload.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dagy.cafemania.shared.constant.AppConstant.APP_ROOT_URL;

public interface ProductApi {

    @PostMapping(
            value = APP_ROOT_URL + "/categories/{categoryId}/products",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Création d'un produit ",
            description = "Ajouter un nouveau produit à une catégorie spécifique"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> save(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductRequest request);

    @GetMapping(
            value = APP_ROOT_URL + "/categories/{categoryId}/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Trouver un produit par ID appartenant à une catégorie spécifique",
            description = "Retourne un seul produit appartenant à la catégorie spécifique"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> getProductById(
            @PathVariable("categoryId") String categoryId,
            @PathVariable("productId") String productId
    );

    @GetMapping(
            value = APP_ROOT_URL + "/categories/{categoryId}/products",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Récupérer tous les produits",
            description = "Permet d'obtenir la liste de touts les produits. La réponse est une liste de produits"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> getProductsByCategoryId(@PathVariable("categoryId") String categoryId);


    @GetMapping(
            value = APP_ROOT_URL + "/products",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Récupérer toutes les produits",
            description = "Permet d'obtenir la liste de touts les produits. La réponse est une liste de produits"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> findAll();

    @GetMapping(
            value = APP_ROOT_URL + "/products/pagination/{page}/{size}/{field}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Rechercher tous les produits de toutes les catégories en utilisant la pagination",
            description = "Permet d'obtenir la liste de tous les produits." +
                    " La réponse est une liste produits , les paramètres  @PathVariable int page,\n" +
                    "            @PathVariable int size,\n" +
                    "            @PathVariable String field"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> findProductWithPaginationAndSorting(
            @PathVariable int page,
            @PathVariable int size,
            @PathVariable String field);

    @GetMapping(
            value = APP_ROOT_URL + "/products/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Rechercher tous les produits de toutes les catégories en utilisant la pagination",
            description = "Retourne une liste de produits appartenant à n'importe quelle catégorie"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> getAllProductUsingPagination(
            @Valid @RequestBody ProductSearchRequest searchRequest);

    @PutMapping(
            value = APP_ROOT_URL + "/categories/{categoryId}/products/{productId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Modification d'un produits",
            description = "Mettre à jour un produit existant appartenant à la catégorie spécifique"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> update(
            @PathVariable String categoryId,
            @PathVariable String productId,
            @Valid @RequestBody ProductRequest request);

    @DeleteMapping(
            value = APP_ROOT_URL + "/category/{categoryId}/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Supprimé un produit",
            description = "Supprimer un produit appartenant à une catégorie spécifique"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> delete(
            @PathVariable("categoryId") String categoryId,
            @PathVariable("productId") String productId
    );

}
