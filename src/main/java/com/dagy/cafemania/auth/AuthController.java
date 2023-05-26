package com.dagy.cafemania.auth;

import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import com.dagy.cafemania.user.payload.SignInRequest;
import com.dagy.cafemania.user.payload.SignUpRequest;
import com.dagy.cafemania.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.time.LocalDateTime.now;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
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
                        .data(Map.of("users", userService.signup(signUpRequest)))
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiDataResponse> signin(SignInRequest signInRequest) {
        try {
            return ResponseEntity.ok(
                    ApiDataResponse.builder()
                            .time(now())
                            .message("Création du compte utilisateur")
                            .httpStatus(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .data(Map.of("users", userService.signin(signInRequest)))
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(
                ApiDataResponse.builder()
                        .time(now())
                        .message("Création du compte utilisateur")
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .data(Map.of("users", userService.signin(signInRequest)))
                        .build()
        );


    }
}
