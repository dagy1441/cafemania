package com.dagy.cafemania.shared.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppUtils {

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus status) {
        return new ResponseEntity<String>("{\"message\":\"" + message + "\"}", status);
    }
}
