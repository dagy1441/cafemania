package com.dagy.cafemania.user.service;

import com.dagy.cafemania.user.payload.SignUpRequest;
import com.dagy.cafemania.user.payload.SignUpResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService  {
     public SignUpResponse signup(SignUpRequest signUpRequest);

}
