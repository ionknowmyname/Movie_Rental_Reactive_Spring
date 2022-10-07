package com.faithfulolaleru.movieRentalReactive.utils;

import com.faithfulolaleru.movieRentalReactive.dto.InvoiceResponse;
import com.faithfulolaleru.movieRentalReactive.dto.MovieRequest;
import com.faithfulolaleru.movieRentalReactive.dto.MovieResponse;
import com.faithfulolaleru.movieRentalReactive.models.Invoice;
import com.faithfulolaleru.movieRentalReactive.models.Movie;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

public class AppUtils {

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

    public static Mono<AppResponse> buildAppResponse(Object movies, String message) {   // Object can also be List
        return Mono.just(AppResponse.builder()
                .statusCode("200")
                .httpStatus(HttpStatus.OK)
                .message(message)
                .data(movies)
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
}
