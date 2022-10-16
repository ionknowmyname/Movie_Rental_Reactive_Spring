package com.faithfulolaleru.movieRentalReactive.config;

import com.faithfulolaleru.movieRentalReactive.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private JwtUtils jwtUtils;

    private UserRepository userRepository;



    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String userName = jwtUtils.getUsernameFromToken(token);
        Claims claims = jwtUtils.getClaimsFromToken(token);
        List<String> role = claims.get("role", List.class);
        List<SimpleGrantedAuthority> authorities = role.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userName, null, authorities
        );

        return userRepository.findByUsername(userName)
                .flatMap(user -> {
                    if(userName.equals(user.getUsername()) && jwtUtils.isTokenValidated(token)) {
                        return Mono.just(authentication);
                    } else {
                        return Mono.empty();
                    }
                });



        /*  1st Impl

        return userRepository.findByUsername(userName)
                .flatMap(user -> {
                    if(userName.equals(user.getUsername()) && jwtUtils.isTokenValidated(token)) {
                        return Mono.just(authentication);
                    } else {
                        return Mono.empty();
                    }
                });

          */
    }
}
