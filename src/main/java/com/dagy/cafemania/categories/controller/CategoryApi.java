package com.dagy.cafemania.categories.controller;

import com.dagy.cafemania.categories.Category;
import com.dagy.cafemania.categories.payload.CategoryRequest;
import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import com.dagy.cafemania.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dagy.cafemania.shared.constant.AppConstant.APP_ROOT_URL;

public interface CategoryApi {
    @PostMapping(
            value = APP_ROOT_URL + "/categories",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Création d'une categorie ",
            description = "Permet de créer une nouvelle catégorie. La réponse est un objet de catégorie"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Category.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> save(@RequestBody CategoryRequest request);


    @GetMapping(
            value = APP_ROOT_URL + "/categories/{idCategory}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Récupérer tous les catégories",
            description = "Permet d'obtenir la liste de toutes les catégories. La réponse est une liste catégories"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Category.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> findById(@PathVariable("idCategory") String idCategory);


    @GetMapping(
            value = APP_ROOT_URL + "/categories",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Récupérer toutes les catégorie",
            description = "Permet d'obtenir la liste de toutes les catégories. La réponse est une liste de catégorie"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Category.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> findAll();

    @DeleteMapping(
            value = APP_ROOT_URL + "/categories/{idCategory}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Supprimé une catégorie",
            description = "Permet de supprimer une catégorie en donnant son id. La réponse est un message : Categorie supprimer"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Category.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    ResponseEntity<ApiDataResponse> delete(@PathVariable("idCategory") String idCategory);
    

}
