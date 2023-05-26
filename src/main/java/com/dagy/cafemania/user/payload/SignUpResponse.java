package com.dagy.cafemania.user.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class SignUpResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
