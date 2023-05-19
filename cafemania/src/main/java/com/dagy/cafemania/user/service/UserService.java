package com.dagy.cafemania.user.service;

import com.dagy.cafemania.user.payload.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface UserService  {
     public SignUpResponse signup(SignUpRequest signUpRequest);
     public SignInResponse signin(SignInRequest signInRequest);
     UserDetails loadUserByUsername(String username);

     List<UserResponse> getAllUser();

     public UserResponse update(Integer id, UserRequest request);

     List<UserResponse> findWithSorting(String field);
     Page<UserResponse> findWithPagination(int size, int page);
     Page<UserResponse> findWithPaginationAndSorting(int page, int size, String field);

}
