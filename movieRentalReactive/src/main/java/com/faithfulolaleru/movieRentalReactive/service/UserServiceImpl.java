package com.faithfulolaleru.movieRentalReactive.service;

import com.faithfulolaleru.movieRentalReactive.dto.UserCreateRequest;
import com.faithfulolaleru.movieRentalReactive.exception.ErrorResponse;
import com.faithfulolaleru.movieRentalReactive.exception.GeneralException;
import com.faithfulolaleru.movieRentalReactive.models.User;
import com.faithfulolaleru.movieRentalReactive.repository.UserRepository;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import com.faithfulolaleru.movieRentalReactive.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Mono<AppResponse> createUser(UserCreateRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .map(user -> throwErrorIfExist(user))
                .switchIfEmpty(userRepository.save(User.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .email(request.getEmail())
                        .phoneNumber(request.getPhoneNumber())
                        .role(request.getRole())
                        .walletBalance(request.getWalletBalance())
                        .build()))
                .map(AppUtils::entityToDto2)
                .flatMap(o -> AppUtils.buildAppResponse(o, "Created Successfully"));
    }



    private User throwErrorIfExist(User user) {
        String message = "User with username '" + user.getUsername() + "' already exists";
        throw new GeneralException(HttpStatus.CONFLICT, ErrorResponse.ERROR_USER_ALREADY_EXIST, message);
    }
}
