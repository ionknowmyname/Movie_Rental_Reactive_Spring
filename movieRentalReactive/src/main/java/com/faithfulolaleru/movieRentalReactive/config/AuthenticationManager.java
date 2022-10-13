package com.faithfulolaleru.movieRentalReactive.config;

import com.faithfulolaleru.movieRentalReactive.repository.UserRepository;
import com.faithfulolaleru.movieRentalReactive.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private JwtUtils jwtUtils;

    private UserRepository userRepository;



    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String userName = jwtUtils.getUsernameFromToken(token);

        return userRepository.findByUsername(userName)
                .flatMap(user -> {
                    if(userName.equals(user.getUsername()) && jwtUtils.isTokenValidated(token)) {
                        return Mono.just(authentication);
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
