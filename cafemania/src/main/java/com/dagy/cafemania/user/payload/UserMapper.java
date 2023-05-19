package com.dagy.cafemania.user.payload;

import com.dagy.cafemania.user.User;

public class UserMapper {
    public static UserResponse fromEntity(User user) {
        if (user == null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getFirstName())
                .email(user.getEmail())
                .phoneNumber(user.getPhone())
                .status(user.getStatus())
                .build();
    }

//    public static User toEntity(UserRequest userRequest) {
//        if (userRequest == null) return null;
//        User user = new User();
//        user.setId(userRequest.id());
//        user.setFirstName(userRequest.name());
//        return user;
//    }
}
