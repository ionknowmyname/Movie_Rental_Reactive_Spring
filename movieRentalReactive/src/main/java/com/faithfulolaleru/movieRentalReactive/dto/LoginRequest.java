package com.faithfulolaleru.movieRentalReactive.dto;

import com.faithfulolaleru.movieRentalReactive.utils.ValidPassword;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class LoginRequest {

    @NotEmpty
    @ValidPassword
    private String password;

    @NotEmpty
    private String username;

}
