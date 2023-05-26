package com.dagy.cafemania.user.controller;

import com.dagy.cafemania.shared.exceptions.ResourceNotFoundException;
import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import com.dagy.cafemania.user.payload.UserRequest;
import com.dagy.cafemania.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

import static com.dagy.cafemania.shared.constant.AppConstant.INTERNAL_SERVER_ERROR_RESPONSE;
import static java.time.LocalDateTime.now;

@Slf4j
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

    @PreAuthorize("hasAuthority('admin:read')")
    @Override
    public ResponseEntity<ApiDataResponse> getUserById(Integer id) {
        try {
            return ResponseEntity.ok(
                    ApiDataResponse.builder()
                            .time(now())
                            .message("Récupération de tous les utilisateurs")
                            .httpStatus(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .data(Map.of("users", userService.findById(id)))
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
                        .data(Map.of("users", userService.findWithSorting(field)))
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
                        .data(Map.of("users", userService.findWithPaginationAndSorting(page, size, field)))
                        .build()
        );
    }

    @PreAuthorize("hasAuthority('admin:update')")
    @Override
    public ResponseEntity<ApiDataResponse> enableOrDesableUser(Integer id) {
        try {
            userService.enableOrDisableUser(id);
            return ResponseEntity.ok(
                    ApiDataResponse.builder()
                            .time(LocalDateTime.now())
                            .message("Utilisateur activé ou désactivé avec succès")
                            .httpStatus(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(INTERNAL_SERVER_ERROR_RESPONSE);
        }
    }

}
