package com.dagy.cafemania.user.controller;

import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import com.dagy.cafemania.shared.utilities.AppUtils;
import com.dagy.cafemania.user.payload.SignUpRequest;
import com.dagy.cafemania.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.dagy.cafemania.shared.constant.AppConstant.SOMETHING_WENT_WRONG;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;
    @Override
    public ResponseEntity<String> signUp(SignUpRequest signUpRequest) {
        try {
            userService.signup(signUpRequest);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return AppUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
