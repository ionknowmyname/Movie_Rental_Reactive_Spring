package com.faithfulolaleru.movieRentalReactive.dto;

import com.faithfulolaleru.movieRentalReactive.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Role role;
    private Double walletBalance;

}
