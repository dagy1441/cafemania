package com.dagy.cafemania.user.payload;

import com.dagy.cafemania.user.Role;
import com.dagy.cafemania.user.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class UserResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("email")
    private String email;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("roles")
    private Role role;
}
