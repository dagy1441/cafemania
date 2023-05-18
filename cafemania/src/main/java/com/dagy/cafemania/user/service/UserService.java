package com.dagy.cafemania.user.service;

import com.dagy.cafemania.user.payload.SignInRequest;
import com.dagy.cafemania.user.payload.SignInResponse;
import com.dagy.cafemania.user.payload.SignUpRequest;
import com.dagy.cafemania.user.payload.SignUpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface UserService  {
     public SignUpResponse signup(SignUpRequest signUpRequest);
     public SignInResponse signin(SignInRequest signInRequest);

     UserDetails loadUserByUsername(String username);

}
