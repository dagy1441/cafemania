package com.dagy.cafemania.user.controller;


import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import com.dagy.cafemania.user.User;
import com.dagy.cafemania.user.payload.SignInRequest;
import com.dagy.cafemania.user.payload.SignUpRequest;
import com.dagy.cafemania.user.payload.UserRequest;
import com.dagy.cafemania.user.payload.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dagy.cafemania.shared.constant.AppConstant.APP_ROOT_URL;

@Tag(name = "User", description = "Users APIs")
@RestController
public interface UserApi {

    @PostMapping(
            value = APP_ROOT_URL+ "/users/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Création du compte utilisateur",
            description = "Permet à utilisateurs de crée son compte. La réponse est un objet avec son access_token"
            )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema()) }) })
    ResponseEntity<ApiDataResponse> signUp(@RequestBody SignUpRequest signUpRequest);

    @PostMapping(
            value = APP_ROOT_URL+ "/users/signin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Connexion de l'utilisateur",
            description = "Permet utilisateurs de se connecter. La réponse est un objet avec son access_token "
            )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema()) }) })
    ResponseEntity<ApiDataResponse> signin(@RequestBody SignInRequest signInRequest);

    @GetMapping(
            value = APP_ROOT_URL+ "/users/getall",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Récupérer tous les utilisateurs",
            description = "Permet d'obtenir la liste de tous les utilisateurs. La réponse est une liste utilisateurs"
            )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema()) }) })
     ResponseEntity<ApiDataResponse> getUsers();

    @PutMapping(
            value = APP_ROOT_URL+ "/users/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Création du compte utilisateur",
            description = "Permet à utilisateurs de crée son compte. La réponse est un objet avec son access_token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema()) }) })
    ResponseEntity<ApiDataResponse> update(@PathVariable Integer id,@RequestBody UserRequest request);

    @GetMapping(
            value = APP_ROOT_URL+ "/users/sorting/{field}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Récupération de tous les utilisateurs par paramètre @email ou firstName",
            description = "Permet d'obtenir la liste de tous les utilisateurs. La réponse est une liste utilisateurs"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema()) }) })
    ResponseEntity<ApiDataResponse> findUsersWithSorting(@PathVariable String field);

    @GetMapping(
            value = APP_ROOT_URL+ "/users/pagination/{page}/{size}/{field}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Récupération de tous les utilisateurs par pagination et par ordre decroissant",
            description = "Permet d'obtenir la liste de tous les utilisateurs." +
                    " La réponse est une liste utilisateurs , les paramètres  @PathVariable int page,\n" +
                    "            @PathVariable int size,\n" +
                    "            @PathVariable String field"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema()) }) })
    ResponseEntity<ApiDataResponse> findUsersWithPaginationAndSorting(
            @PathVariable int page,
            @PathVariable int size,
            @PathVariable String field);

}
