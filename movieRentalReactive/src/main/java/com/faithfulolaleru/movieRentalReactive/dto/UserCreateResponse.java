package com.faithfulolaleru.movieRentalReactive.dto;

import com.faithfulolaleru.movieRentalReactive.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateResponse {

    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String phoneNumber;
    private Role role;
    private Double walletBalance;
}
