package com.dagy.cafemania.user.controller;

import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import com.dagy.cafemania.shared.utilities.AppUtils;
import com.dagy.cafemania.user.payload.SignInRequest;
import com.dagy.cafemania.user.payload.SignUpRequest;
import com.dagy.cafemania.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.dagy.cafemania.shared.constant.AppConstant.INTERNAL_SERVER_ERROR_RESPONSE;
import static com.dagy.cafemania.shared.constant.AppConstant.SOMETHING_WENT_WRONG;
import static java.time.LocalDateTime.now;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<ApiDataResponse> signUp(SignUpRequest signUpRequest) {
//        try {
//            return ResponseEntity.ok(
//                    ApiDataResponse.builder()
//                            .time(now())
//                            .message("Création du compte utilisateur")
//                            .httpStatus(HttpStatus.OK)
//                            .statusCode(HttpStatus.OK.value())
//                            .data(Map.of("users",  userService.signup(signUpRequest)))
//                            .build()
//            );
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        return ResponseEntity.internalServerError().body(INTERNAL_SERVER_ERROR_RESPONSE);
        return ResponseEntity.ok(
                ApiDataResponse.builder()
                        .time(now())
                        .message("Création du compte utilisateur")
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .data(Map.of("users",  userService.signup(signUpRequest)))
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiDataResponse> signin(SignInRequest signInRequest) {
        return ResponseEntity.ok(
                ApiDataResponse.builder()
                        .time(now())
                        .message("Création du compte utilisateur")
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .data(Map.of("users",  userService.signin(signInRequest)))
                        .build()
        );

    }

    @Override
    public ResponseEntity<ApiDataResponse> getUsers(SignUpRequest signUpRequest) {
        try {
            return ResponseEntity.ok(
                    ApiDataResponse.builder()
                            .time(now())
                            .message("Récupération de tous les utilisateurs")
                            .httpStatus(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .data(Map.of("users",  userService.signup(signUpRequest)))
                            .build()
            );

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(INTERNAL_SERVER_ERROR_RESPONSE);
    }
}
