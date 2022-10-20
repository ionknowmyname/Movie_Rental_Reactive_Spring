package com.faithfulolaleru.movieRentalReactive.service;

import com.faithfulolaleru.movieRentalReactive.dto.UserCreateRequest;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<AppResponse> createUser(UserCreateRequest request);
}
