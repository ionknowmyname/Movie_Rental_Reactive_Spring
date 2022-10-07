package com.faithfulolaleru.movieRentalReactive.controllers;

import com.faithfulolaleru.movieRentalReactive.dto.LoginRequest;
import com.faithfulolaleru.movieRentalReactive.dto.LoginResponse;
import com.faithfulolaleru.movieRentalReactive.exception.GeneralException;
import com.faithfulolaleru.movieRentalReactive.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/auth")

@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;



    @PostMapping(value = "/generate-token")
    public ResponseEntity<LoginResponse> generateToken(@RequestBody LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException exception) {
            log.error(exception.getMessage());
            throw new GeneralException(
                    HttpStatus.BAD_REQUEST,
                    SpacesApiErrorResponse.ERROR_INVALID_USER_CREDENTIALS,
                    messageProvider.getInvalidUserCredentialsErrorMessage()
            );
        }


        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthTokenDto token = tokenService.generateToken(authentication);
        return ResponseEntity.ok().body(token);
    }
}
