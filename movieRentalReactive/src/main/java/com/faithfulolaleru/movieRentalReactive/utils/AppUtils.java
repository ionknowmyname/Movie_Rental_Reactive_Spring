package com.faithfulolaleru.movieRentalReactive.utils;

import com.faithfulolaleru.movieRentalReactive.dto.InvoiceResponse;
import com.faithfulolaleru.movieRentalReactive.dto.MovieRequest;
import com.faithfulolaleru.movieRentalReactive.dto.MovieResponse;
import com.faithfulolaleru.movieRentalReactive.dto.UserCreateResponse;

import com.faithfulolaleru.movieRentalReactive.models.Invoice;
import com.faithfulolaleru.movieRentalReactive.models.Movie;
import com.faithfulolaleru.movieRentalReactive.models.User;
import com.faithfulolaleru.movieRentalReactive.repository.UserRepository;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Component
public class AppUtils {

    private final UserRepository userRepository;

    public static MovieResponse entityToDto(Movie movie) {
        MovieResponse response = new MovieResponse();
        BeanUtils.copyProperties(movie, response);

        return response;
    }

    public static MovieResponse entityToDto2(Movie movie) {
        MovieResponse response = MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .yearReleased(movie.getYearReleased())
                .mainActor(movie.getMainActor())
                .isBlockBuster(movie.getIsBlockBuster())
                .perDayCost(movie.getPerDayCost())
                .build();

        return response;
    }

    public static UserCreateResponse entityToDto2(User user) {
        UserCreateResponse response = UserCreateResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .walletBalance(user.getWalletBalance())
                .build();

        return response;
    }

    public static InvoiceResponse entityToDto2(Invoice invoice) {
        InvoiceResponse response = InvoiceResponse.builder()
                .id(invoice.getId())
                .movie(invoice.getMovie())
                .rentStartDate(invoice.getRentStartDate())
                .rentDays(invoice.getRentDays())
                .rentCost(invoice.getRentCost())
                .build();

        return response;
    }

    public Movie dtoToEntity(MovieRequest request) {
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .yearReleased(request.getYearReleased())
                .mainActor(request.getMainActor())
                .isBlockBuster(request.getIsBlockBuster())
                .perDayCost(request.getPerDayCost())
                .build();

        return movie;
    }

    public static Mono<AppResponse> buildAppResponse(Object data, String message) {   // Object can also be List
        return Mono.just(AppResponse.builder()
                .statusCode("200")
                .httpStatus(HttpStatus.OK)
                .message(message)
                .data(data)
                .build());
    }

    public static Mono<AppResponse> buildAppResponse(String message) {   // Object can also be List
        return Mono.just(AppResponse.builder()
                .statusCode("200")
                .httpStatus(HttpStatus.OK)
                .message(message)
                .data(null)
                .build());
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return findUserByUsername(username);
    }

    public User findUserByUsername(@NotNull String username) {
        Mono<User> userMono = userRepository.findByUsername(username);

        return userMono.block();
    }
}
