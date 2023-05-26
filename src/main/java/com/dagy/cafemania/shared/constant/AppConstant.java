package com.dagy.cafemania.shared.constant;

import com.dagy.cafemania.shared.helpers.ApiDataResponse;
import org.springframework.http.HttpStatus;

import static java.time.LocalDateTime.now;

public class AppConstant {
    public final static String APP_KEY = "DSF";
    public final static String JWT_SECRET_KEY = "DSF";

    public static final String APP_ROOT_URL = "/cafemania/api/v1";
    public static final String SOMETHING_WENT_WRONG = "Une erreur interne c'est produite";
    public static final ApiDataResponse INTERNAL_SERVER_ERROR_RESPONSE = ApiDataResponse.builder()
            .time(now())
            .message("Une Erreur c'est produite")
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .build();

}
