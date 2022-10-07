package com.faithfulolaleru.movieRentalReactive.dto;

import lombok.Builder;
import lombok.Value;


@Builder
@Value    // final of @Data
public class LoginResponse {

    String token;
    String userId;
    String role;
}
