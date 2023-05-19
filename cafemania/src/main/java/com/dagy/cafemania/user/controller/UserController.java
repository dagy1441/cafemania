package com.dagy.cafemania.user.controller;

import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import com.dagy.cafemania.user.payload.UserRequest;
import com.dagy.cafemania.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.dagy.cafemania.shared.constant.AppConstant.INTERNAL_SERVER_ERROR_RESPONSE;
import static java.time.LocalDateTime.now;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController implements UserApi {

    private final UserService userService;

    @PreAuthorize("hasAuthority('admin:read')")
    @Override
    public ResponseEntity<ApiDataResponse> getUsers() {
        try {
            return ResponseEntity.ok(
                    ApiDataResponse.builder()
                            .time(now())
                            .message("Récupération de tous les utilisateurs")
                            .httpStatus(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .data(Map.of("users", userService.getAllUser()))
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(INTERNAL_SERVER_ERROR_RESPONSE);
    }

    @PreAuthorize("hasAuthority('admin:update')")
    @Override
    public ResponseEntity<ApiDataResponse> update(Integer id, UserRequest request) {
        try {
            return ResponseEntity.ok(
                    ApiDataResponse.builder()
                            .time(now())
                            .message("Récupération de tous les utilisateurs")
                            .httpStatus(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .data(Map.of("users", userService.update(id, request)))
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(INTERNAL_SERVER_ERROR_RESPONSE);

    }

    @PreAuthorize("hasAuthority('admin:read')")
    @Override
    public ResponseEntity<ApiDataResponse> findUsersWithSorting(String field) {
        return ResponseEntity.ok(
                ApiDataResponse.builder()
                        .time(now())
                        .message("Récupération de tous les utilisateurs")
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .data(Map.of("users",  userService.findWithSorting(field)))
                        .build()
        );
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @Override
    public ResponseEntity<ApiDataResponse> findUsersWithPaginationAndSorting(int page, int size, String field) {
        return ResponseEntity.ok(
                ApiDataResponse.builder()
                        .time(now())
                        .message("Récupération de tous les utilisateurs")
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .data(Map.of("users",  userService.findWithPaginationAndSorting(page, size, field)))
                        .build()
        );
    }
}
