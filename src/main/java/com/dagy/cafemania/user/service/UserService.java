package com.dagy.cafemania.user.service;

import com.dagy.cafemania.user.payload.*;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    SignUpResponse signup(SignUpRequest signUpRequest);

    SignInResponse signin(SignInRequest signInRequest);

    UserDetails loadUserByUsername(String username);

    List<UserResponse> getAllUser();

    List<String> findByRole(String role);

    UserResponse findById(Integer id);

    UserResponse update(Integer id, UserRequest request);

    void enableOrDisableUser(Integer id);

    List<UserResponse> findWithSorting(String field);

    Page<UserResponse> findWithPagination(int size, int page);

    Page<UserResponse> findWithPaginationAndSorting(int page, int size, String field);

}
