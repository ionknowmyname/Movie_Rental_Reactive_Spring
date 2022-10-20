package com.faithfulolaleru.movieRentalReactive.controllers;

import com.faithfulolaleru.movieRentalReactive.config.AuthenticationManager;
import com.faithfulolaleru.movieRentalReactive.config.JwtUtils;
import com.faithfulolaleru.movieRentalReactive.dto.LoginRequest;
import com.faithfulolaleru.movieRentalReactive.dto.LoginResponse;
import com.faithfulolaleru.movieRentalReactive.exception.GeneralException;
import com.faithfulolaleru.movieRentalReactive.repository.UserRepository;
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
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("api/v1/auth")

@RequiredArgsConstructor
public class AuthController {

    // private final TokenService tokenService;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;



//    @PostMapping(value = "/generate-token")
//    public ResponseEntity<LoginResponse> generateToken(@RequestBody LoginRequest request) {
        /*

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

        */


        /*return userRepository.findByUsername(request.getUsername())
                .map(user -> )*/
//    }




    @PostMapping(value = "/login")
    public Mono<ServerResponse> generateToken(ServerRequest request) {
        Mono<LoginRequest> loginRequestMono = request.bodyToMono(LoginRequest.class);

        return loginRequestMono.flatMap(loginRequest -> userRepository.findByUsername(loginRequest.getUsername())
                .flatMap(user -> {
                    if(loginRequest.getPassword().equals(user.getPassword())) {
                        return ServerResponse.ok().bodyValue(LoginResponse.builder()
                                .token(jwtUtils.generateToken(user))
                                .userId(user.getId())
                                .role(user.getRole().toString())
                                .build());
                    } else {
                        // return ResponseEntity.status().body().build();
                        return ServerResponse.badRequest().build();
                    }
                })
                .switchIfEmpty(ServerResponse.notFound().build())
        );
    }
}
