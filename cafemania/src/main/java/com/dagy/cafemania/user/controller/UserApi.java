package com.dagy.cafemania.user.controller;


import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import com.dagy.cafemania.user.User;
import com.dagy.cafemania.user.payload.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.dagy.cafemania.shared.constant.AppConstant.APP_ROOT_URL;

@Tag(name = "Tutorial", description = "Tutorial management APIs")
@RestController
public interface UserApi {

    @PostMapping(
            value = APP_ROOT_URL+ "/users/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Retrieve a Tutorial by Id",
            description = "Get a Tutorial object by specifying its id. The response is Tutorial object with id, title, description and published status.",
            tags = { "tutorials", "get" })
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
    ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest);

}
